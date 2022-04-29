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


  let i;
  let actual_date_input;
  async function change_date(idx, actual_date_end) {
    console.log(rents)
    // id user, id rent, end date
    let date_end_post = document.getElementById("actual_date").value;
    let user_id_current_url = document.URL.split("/")[5];
    // console.log(rents[idx])

    const res2 = await fetch(`http://localhost:8080/user`);
    const user_array = await res2.json();
    let user_obj = user_array.find((elem) => elem.id == user_id_current_url);

    // console.log(user_obj)
    // console.log(rents)
    // console.log(date_end_post)
    const res3 = await fetch(
      `http://localhost:8080/user/${user_id_current_url}/rent/${rents[idx].id}/return`,
      {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          idUser: user_obj,
          idRent: rents[idx],
          end: date_end_post
        }),
      }
    );
    const data = await res3.json();
    // console.log(data)
  }

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
        <th>Vera data di fine</th>
        <!-- <td><a href={`#/user/${user.id}`}> {user.name}</a></td> -->
      </tr>
      {#each rents as rent,i}
        <tr>
          <td><p>{rent.movie.title}</p></td>
          <td><p>{rent.start}</p></td>
          <td><p>{rent.end}</p></td>
          <td><p>{rent.price}</p></td>
          <td><p>{rent.deposit}</p></td>
          {#if rent.actualEnd == null}
          <td><input id="actual_date"  type="date" on:change = {() => change_date(i)} /></td>
          {:else}
          <td><p>{rent.actualEnd}</p></td>
          {/if}
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
