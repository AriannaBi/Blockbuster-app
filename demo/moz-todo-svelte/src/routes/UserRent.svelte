<script>
  import {
    MaterialApp,
    Button,
    Row,
    Col,
    Select,
    Radio,
  } from "svelte-materialify";

  const items = [];
  let date_start = new Date();
  let date_end = new Date();
  let group;
  let disabledGroup = null;
  let rents = [];
  async function find_rent() {
    let user_id_current_url = document.URL.split("/")[5];
    const response = await fetch(
      `http://localhost:8080/user/${user_id_current_url}/rent`
    );
    const data = await response.json();
    rents = data;
  }

  //   const movies = [];
  let movie_select;
  async function fetch_movie() {
    const response = await fetch(`http://localhost:8080/movie`);
    const data = await response.json();
    data.forEach((elem) => {
      items.push(elem.title);
    });
  }

  async function create_rent() {
    let user_id_current_url = document.URL.split("/")[5];

    const res = await fetch(`http://localhost:8080/movie`);
    const movie_array = await res.json();
    let movie_obj = movie_array.find((elem) => elem.title == movie_select);
    console.log(movie_obj.id);

    console.log(user_id_current_url);
    const res2 = await fetch(`http://localhost:8080/user`);
    const user_array = await res2.json();
    let user_obj = user_array.find((elem) => elem.id == user_id_current_url);

    // let a = date_start;
    // console.log(a)
    const res3 = await fetch(
      `http://localhost:8080/user/${user_id_current_url}/rent`,
      {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          movieId: movie_obj.id,
          start: date_start,
          end: date_end,
        }),
      }
    );
    const data = await res3.json();
  }

  let user_name;
  async function find_user_name() {
    let user_id_current_url = document.URL.split("/")[5];
    const res2 = await fetch(`http://localhost:8080/user/${user_id_current_url}`);
    const user_obj = await res2.json();
    user_name = user_obj.name;
  }

  find_rent();
  fetch_movie();
  find_user_name()
</script>

<div id="select">
  <p>The user is {user_name}</p>
<p>Create a new rental:</p>
<MaterialApp>
  
    <!-- <Row> -->
    <Select {items} bind:value={movie_select}>Nome</Select>
    <!-- </Row> -->

    <div id="date">
      <p>Start</p>
      <input type="date" bind:value={date_start} />
    
      <p>End</p>
      <input type="date" bind:value={date_end} />
    </div>
  

  <Button on:click={create_rent}>Post it</Button>
</MaterialApp>
</div>

<p id="rout">The rentals:</p>
{#if rents.length == 0}
  <p>Your rent list is empty</p>
{/if}

{#if rents.length != 0}
  <div id="div_table_rent">
    <table id="table_rent">
      <tr>
        <th>Film</th>
        <th>Data di inizio</th>
        <th>presunta data di fine</th>
        <th>Noleggio payed</th>
        <th>Deposito versato</th>
        <th>vera data di fine</th>
        <!-- <td><a href={`#/user/${user.id}`}> {user.name}</a></td> -->
      </tr>
      {#each rents as rent}
        <tr>
          <td><p>{rent.movie.title}</p></td>
          <td><p>{rent.start}</p></td>
          <td><p>{rent.end}</p></td>
          <td><p>{rent.price}</p></td>
          <td><p>{rent.deposit}</p></td>
        </tr>
      {/each}
    </table>
  </div>
{/if}

<style>
  #rout {
    margin-top: 8%;
  }
  #date {
    margin-top: 2%;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: baseline;
  }
  
  #select {
    margin-top: 5%;
  }

  #table_rent {
    font-family: Arial, Helvetica, sans-serif;
    border-collapse: collapse;
    width: 70%;
  }

  #div_table_rent {
    display: grid;
    place-items: center;
  }

  #table_rent td {
    /* border: 1px solid #ddd; */
    padding: 4px;
  }

  th {
    padding: 6px;
  }

  /* #table_rent tr:nth-child(even) {
    background-color: #f2f2f2;
  } */

  #table_rent tr:hover {
    background-color: #ddd;
  }
</style>
