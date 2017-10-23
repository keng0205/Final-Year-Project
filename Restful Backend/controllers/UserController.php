<?php
namespace app\controllers;
use \app\models\UserToSubject;
use yii\web\UploadedFile;
use \app\models\Users;
use Yii;

class UserController extends \yii\rest\Controller
{

	
	public function actionRegisterDevice(){
    $response = [];
    $headers=Yii::$app->getRequest()->getHeaders();
    $access_token=$headers['authorization'];
    $user=Users::findIdentityByAccessToken($access_token); 
    if(empty($access_token)||$user==null)
    {
         $response = [
        'status' => '403',
        'errors' => 'Authentication token missing!'
        ,
        'results' => null,
      ];
    }
    else{
    $data = json_decode(Yii::$app->getRequest()->getRawBody(), true);
    if(empty($data['token'])){
      $response = [
        'status' => '401',
        'errors' => 'Token cannot be empty!'
        ,
        'results' => NULL,
      ];
    }
    else{
              
        if(!empty($user)){
         $user->device_tokens=$data['token'];
         $user->save();
            $response = [
              'status' => '200',
              'error' => NULL,
              'results' =>NULL,
                        ]; 
        }
        // Jika username tidak ditemukan bikin response kek gini
        else{
          $response = [
            'status' => '401',
            'message' => 'Invalid User!',
            'data' => '',
          ];
        }
    }
  }
    return $response;
}

  
  public function actionRegisterTutorExtra(){
  
    $response = [];
    $headers=Yii::$app->getRequest()->getHeaders();
    $access_token=$headers['authorization'];
    $user=Users::findIdentityByAccessToken($access_token); 
    if(empty($access_token)||$user==null)
    {
         $response = [
        'status' => '403',
        'errors' => 'Authentication token missing!'
        ,
        'results' => null,
      ];
    }
    else{

    
    $data = json_decode(Yii::$app->getRequest()->getRawBody(), true);
    $errors=[];
    
    if(empty($data['description'])){
      $errors = [
                  "message"=>"Description is required",
                  "code"=>"ERROR_GENERIC_REQUIRED"
        
                ];
    }
    else
    if(empty($data['subjects']))
    {

        $errors = [
                  "message"=>"At least one subject must be selected",
                  "code"=>"ERROR_GENERIC_REQUIRED"
        
                ];
      }
      else
      {
        
         $user->description=$data['description'];
         $user->price=$data['price'];
         $user->save();
         foreach($data["subjects"] as $subject)
         {
          $UserToSubject= new UserToSubject();
          $UserToSubject->subject_id=$subject['id'];
          $UserToSubject->user_id=$user->id;
          $UserToSubject->save();
         }

         $errors=null;    
   
     }
     $response=[
                "status"=>200,
                "errors"=>$errors,
                "results"=>null,


              ];
    }
    return $response;
}


public function actionRemoveDevice(){
    $response = [];
    $headers=Yii::$app->getRequest()->getHeaders();
    $access_token=$headers['authorization'];
    $user=Users::findIdentityByAccessToken($access_token); 
    if(empty($access_token)||$user==null)
    {
         $response = [
        'status' => '403',
        'errors' => 'Authentication token missing!'
        ,
        'results' => null,
      ];
    }
    else{

    
    $data = json_decode(Yii::$app->getRequest()->getRawBody(), true);
    $response = [];
    
    if(empty($data['token'])){
      $response = [
        'status' => '401',
        'errors' => 'Token cannot be empty!'
        ,
        'results' => '',
      ];
    }
    else{
        
        
        if(!empty($user)){
          if($user->device_tokens==$data['token'])
          {
             $user->device_tokens=NULL;
             $user->save();
             $response = [
              'status' => '200',
              'error' => NULL,
              'results' =>NULL,
                        ]; 
          }
          else{
          $response = [
            'status' => '401',
            'message' => 'Invalid token!',
            'results' => '',
          ];
        }
        }
       
        else{
          $response = [
            'status' => '401',
            'message' => 'Invalid User!',
            'results' => '',
          ];
        }
    }
  }
    return $response;
}

  public function actionUpdateProfile(){
    $response = [];
    $headers=Yii::$app->getRequest()->getHeaders();
    $access_token=$headers['authorization'];
    $user=Users::findIdentityByAccessToken($access_token); 
    if(empty($access_token)||$user==null)
    {
         $response = [
        'status' => '403',
        'errors' => 'Authentication token missing!'
        ,
        'results' => null,
      ];
    }
    else{
    $data = json_decode(Yii::$app->getRequest()->getRawBody(), true);
      if(!empty($data)){
      $userdata=$data['user'];
      $user->first_name=$userdata['first_name'];
      $user->last_name=$userdata['last_name'];
      $user->email=$userdata['email'];
      $user->description=$userdata['description'];
      $user->price=$userdata['price'];
      $user->save();
       $response = [
              'status' => '201',
              'errors' => NULL,
              'results' => [
                             
                                  'id' => $user->id,
                                  'first_name' => $user->first_name,
                                  'last_name'=> $user->last_name,
                                  'email'=>$user->email,
                                  'description'=>$user->description,
                                  'avatar'=>$user->avatar,
                                  'price'=>$user->price,
                                  'user_type'=>[
                                              'type'=> $user->user_types->type
                                              ],
                                  'auth_method'=>[
                                              'type'=> $user->authentication_methods->type
                                              ],
                                  'subjects'=>$user->subjects,
                                  ],
                                  ];
                                  
                                  
    }
    return $response;
  }
}



public function actionChangeAvatar(){
    $response = [];
    $headers=Yii::$app->getRequest()->getHeaders();
    $access_token=$headers['authorization'];
    $user=Users::findIdentityByAccessToken($access_token); 
    if(empty($access_token)||$user==null)
    {
         $response = [
        'status' => '403',
        'errors' => 'Authentication token missing!'
        ,
        'results' => null,
      ];
    }
    else
    {
    $uploads = UploadedFile::getInstancesByName("avatar");
    if (empty($uploads)){
        return "Must upload at least 1 file in upfile form-data POST";
    }
    else
    {
      $savedfiles = [];
     foreach ($uploads as $file){
        $path = "C:\basic\web\images\\";
        $file->saveAs($path.$file->name);
        $user->avatar=$file->name;
        $user->save();
        $name=$file;
        }//Your uploaded file is saved, you can process it further from here
               $response = [
              'status' => '201',
              'errors' => NULL,
              'results' => [
                             
                                  'id' => $user->id,
                                  'first_name' => $user->first_name,
                                  'last_name'=> $user->last_name,
                                  'email'=>$user->email,
                                  'description'=>$user->description,
                                  'avatar'=>$user->avatar,
                                  'price'=>$user->price,
                                  'user_type'=>[
                                              'type'=> $user->user_types->type
                                              ],
                                  'auth_method'=>[
                                              'type'=> $user->authentication_methods->type
                                              ],
                                  'subjects'=>$user->subjects,
                                  ],
                                  ];
    }

    }
    return $response;
  }


protected function verbs()
{
   return [
       'RegisterDevice' => ['POST'],
       'RemoveDevice' => ['PUT'],
       'RegisterTutorExtra'=>['POST'],
       'UpdateProfile'=>['PUT'],
       'ChangeAvatar'=>['POST'],
      
   ];
}
	
	
	
}