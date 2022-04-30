<script>
  import {
    MaterialApp,
    Button,
    Row,
    Col,
    Select,
    Radio,
  } from "svelte-materialify";
  import {
    DataTable,
    DataTableHead,
    DataTableRow,
    DataTableCell,
    DataTableBody,
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
    if (movie_obj === undefined) {
        bad_request = true;
    }
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
    // location.reload();
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
    var day = date[2];
    date[2] = date[0];
    date[0] = day;
    date = `${date[0]}.${date[1]}.${date[2]}`;
    return date;
  }

  find_rent();
  fetch_movie();
  find_user();
</script>

<div id="select">
  <p>The user is <b>{user_name}</b></p>
  <h5 class="mb-16">Crea un nuovo noleggio:</h5>
  {#if !bad_request}
    <MaterialApp>
      <div class="container mb-16">
        <Row class="align-{1}" noGutters style="height:150px">
          <Col>
            <div id="select_movie">
              <Select {items} bind:value={movie_select}>Nome</Select>
            </div>
          </Col>
          <Col>
            <p>Start</p>
            <input type="date" bind:value={date_start} />
            <p>End</p>
            <input type="date" bind:value={date_end} />

            {#if !user_lost_movie}
             <p><Button on:click={has_lost_movie}
                  >Click se ha perso un film</Button
                >
              </p>
            
            {:else}
              <p>Film smarrito</p>
            {/if}
          </Col>
        </Row>
      </div>
      <Button class="mt-10 mb-5" on:click={create_rent}>Crea il noleggio</Button>
      <hr>
    </MaterialApp>
  {:else}
    <h5 id="text_bad">BAD REQUEST: errore nell'inserire i dati</h5>
    <MaterialApp>
      <Button on:click={try_new_rent}>Riprova</Button>
    </MaterialApp>
  {/if}
</div>

<div />

<h5 class="mb-5" id="rout">The rentals:</h5>
{#if rents.length == 0}
  <p>Your rent list is empty</p>
{/if}

{#if rents.length != 0}
  <MaterialApp>
    <DataTable>
      <DataTableHead>
        <DataTableRow>
          <DataTableCell>Film</DataTableCell>
          <DataTableCell>Inizio noleggio</DataTableCell>
          <DataTableCell>Fine noleggio</DataTableCell>
          <DataTableCell>Prezzo</DataTableCell>
          <DataTableCell>Deposito</DataTableCell>
          <DataTableCell>Data di ritorno</DataTableCell>
          <DataTableCell />
        </DataTableRow>
      </DataTableHead>
      <DataTableBody>
        {#each rents as rent, i}
          <DataTableRow>
            <DataTableCell>{rent.movie.title}</DataTableCell>
            <DataTableCell>{reformat_date(rent.start)}</DataTableCell>
            <DataTableCell>{reformat_date(rent.end)}</DataTableCell>
            <DataTableCell>{rent.price}</DataTableCell>
            <DataTableCell>{rent.deposit}</DataTableCell>
            {#if rent.actualEnd == null}
              <DataTableCell
                ><input id="actual_date" type="date" /></DataTableCell
              >
              <DataTableCell>
                <Button on:click={() => change_date(i)}>Aggiorna</Button>
              </DataTableCell>
            {:else}
              <DataTableCell>{reformat_date(rent.actualEnd)}</DataTableCell>
            {/if}
          </DataTableRow>
        {/each}
      </DataTableBody>
    </DataTable>
  </MaterialApp>
{/if}

<style>
  #rout {
    margin-top: 3%;
  }

  #select {
    margin-top: 5%;
  }

  #select_movie {
    width: 90%;
  }

  #text_bad {
    color: red;
    margin-bottom: 2%;
  }
</style>
