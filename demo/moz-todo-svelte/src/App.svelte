<script>
  import Router from "svelte-spa-router";
  import { routes } from "./routes.js";
  export let name;
</script>

<main>
  <h1>{name}!</h1>

  <div >
    <nav>
      <ul>
        <li><a href="#/"> Home </a></li>
        <li><a href="#/user"> Utente </a></li>
        <li><a href="#/rent"> Noleggio </a></li>
        <li><a href="#/movie"> Aggiungi un film </a></li>
      </ul>
    </nav>
  </div>
  <div>
    <Router {routes} />
  </div>
</main>

<style>
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
    position: fixed;
    /* top: 50%; */
    left: 50%;
    /* bring your own prefixes */
    transform: translate(-50%, -50%);
  }

  

  @media (min-width: 640px) {
    main {
      max-width: none;
    }
  }
</style>
