<?php
namespace app\controllers;
use \app\models\Users;
use \app\models\Events;
use Yii;

class EventController extends \yii\rest\Controller
{


    protected function verbs()
  {
     return [
       'index'=>['POST'],
       'remove'=>['PUT'],
       'GetAvailableEvent'=>['GET'],
       'GetEvent'=>['GET'],
       'Reserve'=>['PUT'],
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
    else if($user->user_type!=1)
    {
        $response = [
        'status' => 403,
        'errors' => 'User is not a tutor!'
        ,
        'results' => null,
      ];

    }
    else
    {
       $data = json_decode(Yii::$app->getRequest()->getRawBody(), true);
       $errors=[];
    
                   if(empty($data['start_time'])){
                       $response = [
                      'status' => 400,
                      'errors' => 'Start Time is missing!'
                      ,
                      'results' => null,
                           ];
                    }
                   else
                   {
                    $datetimeFormat = 'Y-m-d H:i:s';
                    $timestamp=strtotime($data['start_time']);
                    $end_time=strtotime($data['start_time'])+3600;
                    $event= new Events();
                    $event->tutor=$user->id;
                    $event->start_time=date($datetimeFormat,$timestamp);
                    $event->end_time=date($datetimeFormat,$end_time);  
                    $event->save();
                        $response = [
                      'status' => 200,
                      'errors' => null,
                      'results' => [
                                    'id'=>$event->id,
                                    'tutor'=>$event->tutor,
                                    'tutee'=>$event->tutee,
                                    'start_time'=>gmdate("Y-m-d\TH:i:s\Z",$timestamp),
                                    'end_time'=>gmdate("Y-m-d\TH:i:s\Z",$end_time),
                                      ],
                           ];
                    
        

                   }
     }
     return $response;
    

    
  }


   public function actionRemove()
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
    else if($user->user_type!=1)
    {
        $response = [
        'status' => 403,
        'errors' => 'User is not a tutor!'
        ,
        'results' => null,
      ];

    }
    else
    {
       $data = json_decode(Yii::$app->getRequest()->getRawBody(), true);
       $errors=[];

   
       
         $datetimeFormat = 'Y-m-d H:i:s';
          $timestamp=strtotime($data['start_time']);
          $start_time=date($datetimeFormat,$timestamp);
          $end_timestamp=strtotime($data['end_time']);
          $end_time=date($datetimeFormat,$end_timestamp);
          $event=Events::findEvent($user->id,$start_time,$end_time);
          $event->delete();

               $response = [
                      'status' => 200,
                      'errors' => null,
                      'results' => null,
                           ];
        
       }
      
        return $response;

     
   }


  public function actionGetAvailableEvent($id)
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


       $events=Events::GetTutorFreeTimes($id);

        foreach($events as $event)
        {
            $start_time=strtotime($event->start_time);
            $end_time=strtotime($event->end_time);
            $event->start_time=gmdate("Y-m-d\TH:i:s\Z",$start_time);
             $event->end_time=gmdate("Y-m-d\TH:i:s\Z",$end_time);
        }

        $response = [
        'status' => 200,
        'errors' => null,
        'results' => $events,
        ];
      }

      return $response;
     }




  public function actionGetEvent()
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
      $tutorEvent=[];
      $tuteeEvent=[];
      
      if($user->user_type==1)
      {
       $tutorEvent=Events::GetTutorOwnTimes($user->id);
         foreach($tutorEvent as $event)
        {
            $start_time=strtotime($event->start_time);
            $end_time=strtotime($event->end_time);
            $event->start_time=gmdate("Y-m-d\TH:i:s\Z",$start_time);
             $event->end_time=gmdate("Y-m-d\TH:i:s\Z",$end_time);
        }
      }
      else
      {
        $tuteeEvent=Events::GetTuteeTimes($user->id);
        foreach($tuteeEvent as $event)
        {
            $start_time=strtotime($event->start_time);
            $end_time=strtotime($event->end_time);
            $event->start_time=gmdate("Y-m-d\TH:i:s\Z",$start_time);
             $event->end_time=gmdate("Y-m-d\TH:i:s\Z",$end_time);
        }
      }

      $response = [
        'status' => 200,
        'errors' => null,
        'results' => [
                      'own_events'=>$tutorEvent,
                      'reserved_events'=>$tuteeEvent,
                      ],
      ];


    }
      return $response;
   }


    public function actionReserve()
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
      $event=Events::ReserveTimeForUser($data['id']);
      if($event->tutee==null&$user->user_type==2)
      {
        $event->tutee=$user->id;
        $event->save();

         $response = [
        'status' => 200,
        'errors' => null,
        'results' => null,
      ];
      }
      else
      {

         $response = [
        'status' => 400,
        'errors' => "Event reserved",
        'results' => null,
      ];
      }

    }
    return $response;
  }




}
	
	
	
