<?php

namespace app\models;

use Yii;
use yii\base\Model;
use yii\data\ActiveDataProvider;
use app\models\Tutorships;

/**
 * TutorshipsSearch represents the model behind the search form about `app\models\Tutorships`.
 */
class TutorshipsSearch extends Tutorships
{
    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['id', 'tutor_id', 'tutee_id'], 'integer'],
            [['created_at'], 'safe'],
        ];
    }

    /**
     * @inheritdoc
     */
    public function scenarios()
    {
        // bypass scenarios() implementation in the parent class
        return Model::scenarios();
    }

    /**
     * Creates data provider instance with search query applied
     *
     * @param array $params
     *
     * @return ActiveDataProvider
     */
    public function search($params)
    {
        $query = Tutorships::find();

        // add conditions that should always apply here

        $dataProvider = new ActiveDataProvider([
            'query' => $query,
        ]);

        $this->load($params);

        if (!$this->validate()) {
            // uncomment the following line if you do not want to return any records when validation fails
            // $query->where('0=1');
            return $dataProvider;
        }

        // grid filtering conditions
        $query->andFilterWhere([
            'id' => $this->id,
            'tutor_id' => $this->tutor_id,
            'tutee_id' => $this->tutee_id,
            'created_at' => $this->created_at,
        ]);

        return $dataProvider;
    }
}
