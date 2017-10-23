<?php

namespace app\models;

use Yii;

/**
 * This is the model class for table "user_to_subject".
 *
 * @property string $id
 * @property integer $user_id
 * @property integer $subject_id
 */
class UserToSubject extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'user_to_subject';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['user_id', 'subject_id'], 'required'],
            [['user_id', 'subject_id'], 'integer'],
            [['user_id', 'subject_id'], 'unique', 'targetAttribute' => ['user_id', 'subject_id'], 'message' => 'The combination of User ID and Subject ID has already been taken.'],
        ];
    }

    /**
     * @inheritdoc
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'user_id' => 'User ID',
            'subject_id' => 'Subject ID',
        ];
    }
}
