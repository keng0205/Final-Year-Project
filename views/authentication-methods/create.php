<?php

use yii\helpers\Html;


/* @var $this yii\web\View */
/* @var $model app\models\AuthenticationMethods */

$this->title = 'Create Authentication Methods';
$this->params['breadcrumbs'][] = ['label' => 'Authentication Methods', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="authentication-methods-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
