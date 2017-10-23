<?php
namespace app\controllers;
use Yii;
use \app\models\Users;
class AuthController extends \yii\rest\Controller
{
   


	public function actionLogin()
  {
    
    $data = json_decode(Yii::$app->getRequest()->getRawBody(), true);
    $response = [];
    
    if(empty($data['email']) || empty($data['password'])){
      $errors=[
                      [
                      'message'=>'Email and Password cannot be empty!',
                      'code'=>'INVALID_LOGIN',],
                      ];
      $response = [
        'status' => 403,
        'errors' => $errors
        ,
        'results' => null,
      ];
    }
    else{
        
       $user = \app\models\Users::findByEmail($data['email']);

        
        if(!empty($user)){
          // check, valid password
          if($user->validatePassword($data['password'])){
            $token=$user->getJWT();
            $response = [
              'status' => 200,
              'errors' => NULL,
              'results' => [
                             'user'=>[
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
                                  'token'=>$token,
                            ]
                        ];
          }
          
          else{
              $errors=[
                      [
                      'message'=>'Wrong Password',
                      'code'=>'INVALID_LOGIN',],
                      ];
            $response = [
              'status' => 401,
              'errors' => $errors,
              'results' => null,
            ];
          }
        }
        // Jika username tidak ditemukan bikin response kek gini
        else{
           $errors=[
                      [
                      'message'=>'Wrong Email',
                      'code'=>'INVALID_LOGIN',],
                      ];
          $response = [
            'status' => 401,
            'errors' => $errors,
            'results' => null,
          ];
        }
    }
    return $response;
}

 public function actionRegister(){
    
 
    $data = json_decode(Yii::$app->getRequest()->getRawBody(), true);
    $response = [];
    $errors=[];
    
   
    if(empty($data['first_name'])||empty($data['last_name'])||empty($data['email'])||empty($data['user_type'])||empty($data['password']))
    { 
      $errors=[
            [
                'message'=>'First name, last name, email, password and user type is required',
                'code'=>'ERROR_GENERIC_REQUIRED',],
                ];
          $response = [
              'status' => 400,
              'errors' => $errors,
              'results' => NULL,
              ];
    }
    else
    {
      $olduser=\app\models\Users::findByEmail($data['email']);
          
          if($olduser!=null)
          {
             $errors=[
                      [
                      'message'=>'User already exists',
                      'code'=>'REGISTER_USER_EXISTS',],
                      ];
                     $response = [
                    'status' => '500',
                    'errors' => $errors,
                    'results' => NULL,
                    ];
          }
          else
          {
                if($data['user_type']=="TUTOR")
                {
                  $data['user_type']=1;
                }
                else
                  {$data['user_type']=2;}

            $data['auth_method']=1;
            $user= new Users();
            $user->attributes=$data;
            $user->save();
            $token=$user->getJWT();
           
                $response = [
                    'status' => '201',
                    'errors' => NULL,
                    'results' => [
                                   'user'=>[
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
                                        'token'=>$token,  
                                  ]
                              ];


                }
    }
        
    return $response;
    }
  



protected function verbs()
{
   return [
       'login' => ['POST'],
       'register'=>['POST'],
      
   ];
}
	
	
	
}