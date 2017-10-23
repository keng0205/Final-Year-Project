<?php
namespace app\controllers;
use \app\models\Users;
use \app\models\Messages;
use \app\notifications\FirebaseCloudMessaging;
use \app\notifications\client;
use paragraph1\phpFCM\Message;
use paragraph1\phpFCM\Recipient\Device;
use Yii;
class MessageController extends \yii\rest\Controller
{


    protected function verbs()
  {
     return [
       'index'=>['POST'],
       'GetMessages'=>['GET'],
       'Latest'=>['GET'],
     ];
  }

  public function actionIndex()
  {
      $response = [];
    $headers=Yii::$app->getRequest()->getHeaders();
    $access_token=$headers['authorization'];
    $user=Users::findIdentityByAccessToken($access_token); 
    if(empty($access_token)||$user==null)
    {
         $response = [
        'status' => 403,
        'errors' => 'Authentication token missing!'
        ,
        'results' => null,
      ];
    }
    else
    {
       $data = json_decode(Yii::$app->getRequest()->getRawBody(), true);
       $errors=[];
    
           if(empty($data['receiver'])){
           $response = [
          'status' => 400,
          'errors' => 'Receiver is missing!',
          'results' => null,];
            }
            else
               if(empty($data['content'])){
           $response = [
          'status' => 400,
          'errors' => 'Content is missing!',
          'results' => null,];
            }
            else
            {
               $message=new Messages();
               $message->sender=$user->id;
               $message->receiver=$data['receiver'];
               $message->content=$data['content'];
               $message->save(); 
               $receiver=Users::findById($data['receiver']); 
                       if($receiver->device_tokens!=null){
                       $results=[
                                            'sender'=>$message->sender,
                                            'receiver'=>$message->receiver,
                                            'content'=>$message->content,
                                            'sent_at'=>gmdate("Y-m-d\TH:i:s\Z"),];
                    
                    $note = Yii::$app->fcm->createNotification("data", $results);
                      
                       $firebaseMessage = Yii::$app->fcm->createMessage();
                      $firebaseMessage->addRecipient(new Device($receiver->device_tokens));
                      $firebaseMessage->setNotification($note);

                     $response = Yii::$app->fcm->send($firebaseMessage);
                      $firebaseStatus=$response->getStatusCode();





                     }
               $response = [
          'status' => 200,
          'errors' => null,
          'results' => [
                                    'id'=>$message->id,
                                    'sender'=>$message->sender,
                                    'receiver'=>$message->receiver,
                                    'content'=>$message->content,
                                    'sent_at'=>gmdate("Y-m-d\TH:i:s\Z"),
                                    'status'=>$firebaseStatus,
                                      ],
          ]; 
            }
     }
     return $response;
  }

   public function actionGetMessages($id,$from,$to)
  {
      $response = [];
    $headers=Yii::$app->getRequest()->getHeaders();
    $access_token=$headers['authorization'];
    $user=Users::findIdentityByAccessToken($access_token); 
    if(empty($access_token)||$user==null)
    {
         $response = [
        'status' => 403,
        'errors' => 'Authentication token missing!'
        ,
        'results' => null,
      ];
    }
    else
    {
      $messages=Messages::GetConversation($user->id,$id,$from,$to);
          foreach($messages as $message)
        {
            $sent_at=strtotime($message->sent_at);
            $message->sent_at=gmdate("Y-m-d\TH:i:s\Z",$sent_at);
        
        }
        $response = [
        'status' => 200,
        'errors' => null,
        'results' => $messages,
      ];

    }
    return $response;
  }


   public function actionLatest()
  {
      $response = [];
    $headers=Yii::$app->getRequest()->getHeaders();
    $access_token=$headers['authorization'];
    $user=Users::findIdentityByAccessToken($access_token); 
    if(empty($access_token)||$user==null)
    {
         $response = [
        'status' => 403,
        'errors' => 'Authentication token missing!'
        ,
        'results' => null,
      ];
    }
    else
    {
      $messages=Messages::GetUserLatestReceivedMessages($user->id);
      foreach($messages as $message)
        {
            $sent_at=strtotime($message->sent_at);
            $message->sent_at=gmdate("Y-m-d\TH:i:s\Z",$sent_at);
        
        }
        $response = [
        'status' => 200,
        'errors' => null,
        'results' => $messages,
      ];

    }
    return $response;
  }



}
	
	
	
