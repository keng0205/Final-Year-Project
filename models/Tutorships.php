<?php

namespace app\models;
use app\models\Users;

use Yii;

/**
 * This is the model class for table "tutorships".
 *
 * @property string $id
 * @property integer $tutor_id
 * @property integer $tutee_id
 * @property string $created_at
 */
class Tutorships extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'tutorships';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['tutor_id', 'tutee_id'], 'required'],
            [['tutor_id', 'tutee_id'], 'integer'],
            [['created_at'], 'safe'],
            [['tutor_id', 'tutee_id'], 'unique', 'targetAttribute' => ['tutor_id', 'tutee_id'], 'message' => 'The combination of Tutor ID and Tutee ID has already been taken.'],
        ];
    }

    /**
     * @inheritdoc
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'tutor_id' => 'Tutor ID',
            'tutee_id' => 'Tutee ID',
            'created_at' => 'Created At',
        ];
    }

    public static function getTutors($id)
    {   
        $tutors=[];

         $tutorsID=static::find()
                    ->where((['tutee_id'=>$id])) 
                    ->all();

        return $tutorsID;

    }

    public static function getTutees($id)
    {

         return static::find()
                    ->where((['tutor_id'=>$id])) 
                    ->all();
    }
}
