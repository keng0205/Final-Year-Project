<?php

namespace app\models;
use yii\db\Expression;
use Yii;

/**
 * This is the model class for table "events".
 *
 * @property string $id
 * @property integer $tutor
 * @property integer $tutee
 * @property string $start_time
 * @property string $end_time
 */
class Events extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'events';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['tutor'], 'required'],
            [['tutor', 'tutee'], 'integer'],
            [['start_time', 'end_time'], 'safe'],
        ];
    }

    /**
     * @inheritdoc
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'tutor' => 'Tutor',
            'tutee' => 'Tutee',
            'start_time' => 'Start Time',
            'end_time' => 'End Time',
        ];
    }
        public static function findEvent($tutor,$start_time,$end_time)
    {

       return static::find() 
                    ->where((['tutor'=>$tutor])) 
                    ->where((['start_time'=>$start_time])) 
                    ->where((['end_time'=>$end_time])) 
                    ->where((['tutee'=>null])) 
                    ->one();

    }

    public static function GetTutorFreeTimes($tutor)
    {
        $sql="SELECT * FROM events WHERE (tutor = $tutor) AND tutee IS NULL";
        return static::findBySql($sql)->all();
  //SELECT * FROM `events` WHERE tutee IS NULL
//SELECT * FROM events WHERE tutor = 6 AND start_time > NOW() AND tutee = NULL
    }

    public static function GetTutorOwnTimes($tutor)
    {
        $sql="SELECT * FROM events WHERE (tutor = $tutor) AND start_time > NOW()";
        return static::findBySql($sql)->all();
     

    }

    public static function GetTuteeTimes($tutee)
    {   $sql="SELECT * FROM events WHERE (tutee = $tutee) AND start_time > NOW()";
        return static::findBySql($sql)->all();


    }


    public static function ReserveTimeForUser($id)
    {
        
       return static::findOne($id);
    }
}
