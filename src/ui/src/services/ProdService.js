import axios from 'axios'

//http://localhost:8080/webCave/rest/ProducteurMenagement

const base = 'http://www.caveweb.net/rest'

export default {
    getProds(userId){
      return axios
        .get(`${base}/ProducteurMenagement/prods`, {params: {userId}})
        .then(r => r.data)
    }
}