<script>
  import Router from "svelte-spa-router";
  import { routes } from "./routes.js";
  import { Menu, Button, List, ListItem, MaterialApp } from 'svelte-materialify';
  export let name;
</script>

<main>
  <h1>{name}!</h1>

  <div >
    <nav>
      <ul>
        <li><a href="#/"> Home </a></li>
        <li><a href="#/user"> Utente </a></li>
        <li><a href="#/movie"> Aggiungi un film </a></li>
      </ul>
    </nav>
  </div>
  <div id = "id_routes">
    <Router {routes} />
  </div>
</main>

<style>
  #id_routes {
    margin: auto;
    width: 90%;
  }
  main {
    text-align: center;
    padding: 1em;
    max-width: 240px;
    margin: 0 auto;
  }

  h1 {
    color: #ff3e00;
    text-transform: uppercase;
    font-size: 4em;
    font-weight: 100;
  }

  ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
    overflow: hidden;
  }

  li {
    float: left;
  }

  li a {
    display: block;
    padding: 8px;
    /* background-color: #dddddd; */
  }

  nav {
    margin-top: 2%;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: baseline;
  }

  

  @media (min-width: 640px) {
    main {
      max-width: none;
    }
  }
</style>
