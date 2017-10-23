<?php

namespace app\models;

use Yii;

/**
 * This is the model class for table "subjects".
 *
 * @property string $id
 * @property string $type
 */
class Subjects extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'subjects';
    }


    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['type'], 'required'],
            [['type'], 'string', 'max' => 64],
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

    public static function findAllSubjects()
    {

        
       return (static::find()->all());
                        

    }
    public static function findSubjectByID($id)
    {

          return static::findOne($id);
                        

    }
    

    public function getUsers() {
    return $this->hasMany(Users::className(), ['id' => 'user_id'])
      ->viaTable('user_to_subject', ['subject_id' => 'id']);
}
}
