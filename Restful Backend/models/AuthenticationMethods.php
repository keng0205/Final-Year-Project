<?php

namespace app\models;

use Yii;

/**
 * This is the model class for table "authentication_methods".
 *
 * @property string $id
 * @property string $type
 */
class AuthenticationMethods extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'authentication_methods';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['type'], 'required'],
            [['type'], 'string', 'max' => 32],
        ];
    }

    /**
     * @inheritdoc
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'type' => 'Type',
        ];
    }

    public function getUsers()
    {
        return $this->hasMany(Users::className(),['auth_method'=>'id']);
    }

}
