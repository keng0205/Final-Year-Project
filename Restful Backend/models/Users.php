<?php

namespace app\models;

use Yii;


/**
 * This is the model class for table "users".
 *
 * @property string $id
 * @property string $first_name
 * @property string $last_name
 * @property string $email
 * @property string $password
 * @property integer $auth_method
 * @property integer $user_type
 * @property string $description
 * @property string $device_tokens
 * @property string $avatar
 * @property integer $price
 */
class Users extends \yii\db\ActiveRecord 
{
    
    use \app\utils\UserTrait;
    /**
     * @inheritdoc
     */
     public static function tableName()
    {
        return 'users';
    }

    public function rules()
    {
        return [
            [['first_name', 'last_name', 'email',], 'required'],
            [['auth_method', 'user_type', 'price'], 'integer'],
            [['description', 'device_tokens', 'avatar'], 'string'],
            [['first_name', 'last_name', 'email'], 'string', 'max' => 64],
            [['password'], 'string', 'max' => 256],
            [['email', 'auth_method'], 'unique', 'targetAttribute' => ['email', 'auth_method'], 'message' => 'The combination of Email and Auth Method has already been taken.'],
        ];
    }

    /**
     * @inheritdoc
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'first_name' => 'First Name',
            'last_name' => 'Last Name',
            'email' => 'Email',
            'password' => 'Password',
            'auth_method' => 'Auth Method',
            'user_type' => 'User Type',
            'description' => 'Description',
            'device_tokens' => 'Device Tokens',
            'avatar' => 'Avatar',
            'price' => 'Price',
        ];
    }

    public function validatePassword($password)
    {
        return $this->password === $password;
    }

    public static function findByEmail($email)
    {

       return static::find()
                    ->where((['email'=>$email])) 
                    ->one();

    }

    public static function findById($id)
    {



       return static::findOne($id);


    }

    public function getUser_types()
    {
        return $this->hasOne(UserTypes::className(),['id'=>'user_type']);
    }

    public function getAuthentication_methods()
    {
        return $this->hasOne(AuthenticationMethods::className(),['id'=>'auth_method']);
    }

    public function getSubjects() {
    return $this->hasMany(Subjects::className(), ['id' => 'subject_id'])
      ->viaTable('user_to_subject', ['user_id' => 'id']);
    }

    protected static function getSecretKey()
    {
        return 'MyTuitionApp';
    }

    public function getId()
    {
        return $this->id;
    }

    


}
