<script>
  import Router from "svelte-spa-router";
  import { routes } from "./routes.js";
  import {
    Menu,
    Button,
    List,
    ListItem,
    MaterialApp,
  } from "svelte-materialify";
  import { AppBar, Icon } from "svelte-materialify";
  export let name;
</script>

<main>
  <!-- <h1>{name}!</h1> -->
  <!-- svelte-ignore a11y-missing-attribute -->
  <img src="./blockbusterLogo.png" height="5%" width="30%" />

  <div id = "main_menu">
    <MaterialApp>
      <AppBar class="d-flex justify-space-around">
        <span class="black-text pa-2 ma-10"><a href="#/"> Overview </a></span>
        <span class="black-text pa-2 ma-10"><a href="#/user"> Utente </a></span>
        <span class="black-text pa-2 ma-10"><a href="#/movie"> Film </a></span>
      </AppBar>
    </MaterialApp>
  </div>
  <div id="id_routes">
    <Router {routes} />
  </div>
</main>

<style>
  #main_menu {
    margin-top: 3%;
  }
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

  a {
    font-size: 120%;
  }

  @media (min-width: 640px) {
    main {
      max-width: none;
    }
  }
</style>
