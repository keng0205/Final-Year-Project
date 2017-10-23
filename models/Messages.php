<?php

namespace app\models;

use Yii;

/**
 * This is the model class for table "messages".
 *
 * @property string $id
 * @property integer $sender
 * @property integer $receiver
 * @property string $content
 * @property string $sent_at
 */
class Messages extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'messages';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['sender', 'receiver', 'content'], 'required'],
            [['sender', 'receiver'], 'integer'],
            [['content'], 'string'],
            [['sent_at'], 'safe'],
        ];
    }

    /**
     * @inheritdoc
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'sender' => 'Sender',
            'receiver' => 'Receiver',
            'content' => 'Content',
            'sent_at' => 'Sent At',
        ];
    }

    public static function GetConversation($firstId,$secondId,$from,$to)
    {
        return static::find()
                    ->where("(sender=$firstId AND receiver=$secondId)")
                    ->orWhere("(sender = $secondId AND receiver = $firstId)")
                        ->orderBy(['id' => SORT_DESC])
                        ->limit($to-$from)
                        ->offset($from)
                        ->all();


    }
    public static function findById($id)
    {
      return static::findOne($id);
    }
    

    public static function GetUserLatestReceivedMessages($id)
    {
        /*
        $receivedMessages= static::find()
                                 ->distinct("sender")
                                 ->where((['receiver'=>$id]))
                                 ->orWhere((['sender'=>$id]))
                                 ->orderBy([
                                              'sender' => SORT_DESC,
                                              'receiver' => SORT_DESC,
                                              'id' => SORT_DESC
                                             ])
                                    ->all();*/


                                    $sql="SELECT t1.*
FROM 
  messages AS t1
LEFT JOIN messages AS t2
  ON ((t1.sender = t2.sender AND t1.receiver = t2.receiver) 
    OR (t1.sender = t2.receiver AND t1.receiver = t2.sender))
    AND t1.id < t2.id
WHERE (t1.sender = $id OR t1.receiver = $id) AND t2.id IS NULL";
$latestMessages=static::findBySql($sql)->all();


        return $latestMessages;


    }

}
