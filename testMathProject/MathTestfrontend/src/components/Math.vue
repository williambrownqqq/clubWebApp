<template>
<div class="row">
    <h2 align="center">Mathematical Equation</h2>
    <div class="col-sm-4">
    <form @submit.prevent="saveData">
    <div class="form-group" align="left">
        <label>Matematical Equation</label>
        <input type="text" v-model="mathematicalequation.mathematicalequation" class="form-control" id="exampleInputEmail1" placeholder="Enter matematical equation">
    </div>
    <div class="form-group" align="left">
        <label>Matematical Equation root 1</label>
        <input type="text" v-model="mathematicalequation.equationroot1" class="form-control" id="exampleInputEmail1" placeholder="Enter 1 root">
    </div>
    <div class="form-group" align="left">
        <label>Matematical Equation root 2</label>
        <input type="text" v-model="mathematicalequation.equationroot2" class="form-control" id="exampleInputEmail1" placeholder="Enter 2 root">
    </div>
    <br>
    <button type="submit" class="btn btn-primary">Submit</button>
    </form>
    </div>

    <div>
      <br>
      <h3>Search equation by root</h3>
      <div>
        <input type="text" v-model="searchQuery" placeholder="Search by word" />
        <button @click="search">Search</button>
      <ul>
        <li v-for="result in searchResults" :key="result.id">{{ result.name }}</li>
      </ul>
  </div>
    </div>
</div>
</template>
<script>
import Vue from 'vue'
import axios from 'axios'
  Vue.use(axios)
  export default {
    name: 'Math',
    data () {
      return {
        result: {},
        mathematicalequation: {
            eq_id: '',
            mathematicalequation: '',
            quationroot1: '',
            quationroot2: ''
        }
      }
    },
    created () {
    },
    mounted () {
          console.log('mounted() called.......')
      },
    methods: {
           saveData () {
            axios.post('http://localhost:8085/api/v1/matheq/save', this.mathematicalequation)
            .then(
              ({data}) => {
                alert('commited')
              }
            )
           }
      }
  }
</script>