<template>
    <div class="container">
        <h2>Prods</h2>
        <h3>{{prods[1].id}}</h3>
    </div>
</template>

<script>

import repository from '../services/ProdService'

export default {
        name: 'Prods',

    data () {
        return {
        prods: [],
        isPageLoading: false
        }
    },

    created(){
        this.fetchData()
    },

    methods: {
        fetchData(){
          repository
            .getProds(20)
            .then(d => {
            this.prods = d 
            })
        },
    }

}
</script>