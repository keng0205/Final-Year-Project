<?php

namespace app\models;

use Yii;

/**
 * This is the model class for table "user_types".
 *
 * @property string $id
 * @property string $type
 */
class UserTypes extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'user_types';
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
        return $this->hasMany(Users::className(),['user_type'=>'id']);
    }
}
