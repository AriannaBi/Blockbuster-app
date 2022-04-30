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
  let bad_request = false;
  let date_start = new Date();
  let date_end = new Date();
  let rents = [];
  let user_name;
  let user_obj;
  

  async function find_rent() {
    let user_id_current_url = document.URL.split("/")[5];
    const response = await fetch(
      `http://localhost:8080/user/${user_id_current_url}/rent`
    );
    const data = await response.json();
    rents = data;
  }
 

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
    const data = await res3.json().catch((error) => {
      bad_request = true;
    });
    find_rent();
    // location.reload();
  }

  
  async function find_user() {
    let user_id_current_url = document.URL.split("/")[5];
    const res2 = await fetch(
      `http://localhost:8080/user/${user_id_current_url}`
    );
    user_obj = await res2.json();
    user_name = user_obj.name;
    user_lost_movie = user_obj.lostBeforeFidelity;
    // console.log(user_obj)
    return user_obj;
  }

  async function change_date(idx) {
    let date_end_post = document.getElementById("actual_date").value;
    let user_id_current_url = document.URL.split("/")[5];
    const res2 = await fetch(`http://localhost:8080/user`);
    const user_array = await res2.json();
    let user_obj = user_array.find((elem) => elem.id == user_id_current_url);

    const res3 = await fetch(
      `http://localhost:8080/user/${user_id_current_url}/rent/${rents[idx].id}`,
      {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          actualEnd: date_end_post,
        }),
      }
    );
    const data = await res3.json();
    find_rent();
  }

  function try_new_rent() {
    bad_request = false;
  }

  let user_lost_movie = false;
  async function has_lost_movie() {
    let user_id_current_url = document.URL.split("/")[5];
    const res3 = await fetch(
      `http://localhost:8080/user/${user_id_current_url}`,
      {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          lostBeforeFidelity: true,
        }),
      }
    );
    user_obj = await res3.json();
    user_lost_movie = true;
    location.reload();
    // console.log(user_obj)
  }

  function reformat_date(date) {
      var day = date[2]
      date[2] = date[0]
      date[0] = day
      date = `${date[0]}.${date[1]}.${date[2]}`
      return date
  }


  find_rent();
  fetch_movie();
  find_user();

</script>

<div id="select">
  <p>The user is {user_name}</p>
  <p>Create a new rental:</p>
  {#if !bad_request}
    <MaterialApp>
      <Select {items} bind:value={movie_select}>Nome</Select>

      <div id="date">
        <p>Start</p>
        <input type="date" bind:value={date_start} />

        <p>End</p>
        <input type="date" bind:value={date_end} />
      </div>

      <Button on:click={create_rent}>Post it</Button>
    </MaterialApp>
  {:else}
    <p>BAD REQUEST: errore nell'inserire i dati</p>
    <MaterialApp>
      <Button on:click={try_new_rent}>Riprova</Button>
    </MaterialApp>
  {/if}
</div>

<div>
{#if !user_lost_movie}
  <MaterialApp
    ><Button on:click={has_lost_movie}>Click se ha perso un film</Button>
  </MaterialApp>
{:else}
  <p>Film smarrito</p>
{/if}
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
        <th />
        <!-- <th /> -->
        <!-- <td><a href={`#/user/${user.id}`}> {user.name}</a></td> -->
      </tr>
      {#each rents as rent, i}
        <tr>
          <td><p>{rent.movie.title}</p></td>
          <!-- <td><p>{rent.start}</p></td> -->
          <td><p>{reformat_date(rent.start)}</p></td>
          <td><p>{reformat_date(rent.end)}</p></td>
          <td><p>{rent.price}</p></td>
          <td><p>{rent.deposit}</p></td>
          {#if rent.actualEnd == null}
            <td><input id="actual_date" type="date" /></td>
            <td>
              <MaterialApp
                ><Button on:click={() => change_date(i)}>Aggiorna</Button
                ></MaterialApp
              >
            </td>
          {:else}
            <td><p>{reformat_date(rent.actualEnd)}</p></td>
            <td />
          {/if}
          <!-- <td>
            <MaterialApp><Button on:click={() => change_date(i)}>Ha perso il film</Button></MaterialApp>
          </td> -->
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
