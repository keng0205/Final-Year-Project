<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model app\models\Tutorships */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="tutorships-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'tutor_id')->textInput() ?>

    <?= $form->field($model, 'tutee_id')->textInput() ?>

    <?= $form->field($model, 'created_at')->textInput() ?>

    <div class="form-group">
        <?= Html::submitButton($model->isNewRecord ? 'Create' : 'Update', ['class' => $model->isNewRecord ? 'btn btn-success' : 'btn btn-primary']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
