<?php
namespace app\controllers;
use \app\models\Users;
use \app\models\Tutorships;
use Yii;
class TutorshipController extends \yii\rest\Controller
{


    protected function verbs()
  {
     return [
       'index'=>['POST'],
       'GetTutorship'=>['GET'],
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
    
         if(empty($data['id'])){
         $response = [
        'status' => 400,
        'errors' => 'ID is missing!'
        ,
        'results' => null,
      ];
              }
            else
            {
                  if($user->user_type==1)
                  {
                    $tutorship= new Tutorships();
                      $tutorship->tutor_id=$user->id;
                      $tutorship->tutee_id=$data['id'];
                      
                      $tutorship->save();
                  }
                  else
                  {

                      $tutorship= new Tutorships();
                      $tutorship->tutee_id=$user->id;
                      $tutorship->tutor_id=$data['id'];
                      
                      $tutorship->save();
                  }
                   $response = [
                                'status' => 200,
                                'errors' => null
                                ,
                                'results' => null,
                              ];

        

            }
     }
     return $response;
    

    
  }

  public function actionGetTutorship(){

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
        $tutors=[];
        $tutees=[];
        $tutorsID=Tutorships::getTutors($user->id);
        $i=0;
        foreach($tutorsID as $tutorID)
        {
            $tutor=Users::findById($tutorID->tutor_id);
            $tutors[$i]=          [ 'id' => $tutor->id,
                                  'first_name' => $tutor->first_name,
                                  'last_name'=> $tutor->last_name,
                                  'email'=>$tutor->email,
                                  'description'=>$tutor->description,
                                  'avatar'=>$tutor->avatar,
                                  'price'=>$tutor->price,
                                  'user_type'=>[
                                              'type'=> $tutor->user_types->type
                                              ],
                                  'auth_method'=>[
                                              'type'=> $tutor->authentication_methods->type
                                              ],
                                  'subjects'=>$tutor->subjects,];
            $i++;
        }
        $i=0;
        $tutees=Tutorships::getTutees($user->id);
        foreach ($tutees as $tutee) {
          $tutee=Users::findById($tutee->tutee_id);
           $tutees[$i]=          [ 'id' => $tutee->id,
                                  'first_name' => $tutee->first_name,
                                  'last_name'=> $tutee->last_name,
                                  'email'=>$tutee->email,
                                  'description'=>$tutee->description,
                                  'avatar'=>$tutee->avatar,
                                  'price'=>$tutee->price,
                                  'user_type'=>[
                                              'type'=> $tutee->user_types->type
                                              ],
                                  'auth_method'=>[
                                              'type'=> $tutee->authentication_methods->type
                                              ],
                                  'subjects'=>$tutee->subjects,];
          $i++;
        }


        $response=[
          'status' => 200,
          'errors' => null,
          'results'=>[ 
                      'tutors'=>$tutors,
                      'tutees'=>$tutees,
                     ],
             
                  ];

      }
      return $response;

  }

  



}
	
	
	
