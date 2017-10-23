<?php
namespace app\controllers;
use Yii;
class SubjectsController extends \yii\rest\Controller
{


    protected function verbs()
  {
     return [
       'index'=>['GET'],
       'SubjectTutors'=>['GET'],
     ];
  }

  public function actionIndex(){
    

    $response = [];
    
 
        
       $subjects = \app\models\Subjects::findAllSubjects();
        
        if(!empty($subjects))
        {
          
            $response = [
              'status' => '200',
              'error' => NULL,
              'results' => 
                             
                            $subjects,
                        
                        ];
          }
          
          else{
            $response = [
              'status' => '400',
              'message' => 'No Subjects!',
              'results' => '',
            ];
          }
  
        
    return $response;
  }

    public function actionSubjectTutors($id){
    
    $response = [];
    
     $results=[];
        
       $subject = \app\models\Subjects::findSubjectByID($id);
        
        if(!empty($subject))
        {$i=0;
        $users= $subject->users;
        foreach($users as $user)
        {
                         $results[$i]=['id'=> $user->id,
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
                                  ];  
                                  $i++;   
        }

           
            $response = [
              'status' => 200,
              'error' => NULL,
              'results' => 
                            $results, 
                            
                              
                              
                        ];
                                  
       }
          
          else{
            $response = [
              'status' => 400,
              'message' => 'No Tutors!',
              'results' => '',
            ];
          }
  
        
    return $response;
  }





}
	
	
	
