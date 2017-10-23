<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model app\models\AuthenticationMethods */

$this->title = 'Update Authentication Methods: ' . $model->id;
$this->params['breadcrumbs'][] = ['label' => 'Authentication Methods', 'url' => ['index']];
$this->params['breadcrumbs'][] = ['label' => $model->id, 'url' => ['view', 'id' => $model->id]];
$this->params['breadcrumbs'][] = 'Update';
?>
<div class="authentication-methods-update">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
