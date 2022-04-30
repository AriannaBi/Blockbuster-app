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
  import { TextField } from "svelte-materialify";
  let group = false;
  let standard;
  let children;
  let newRelease;
  let movie_name;
  let bad_request = false;

  async function create_movie() {
    if (group == 1) {
      standard = true;
      children = false;
      newRelease = false;
    } else if (group == 2) {
      standard = false;
      children = false;
      newRelease = true;
    } else if (group == 3) {
      standard = false;
      children = true;
      newRelease = false;
    } else {
      standard = false;
      children = false;
      newRelease = false;
    }

    const response = await fetch("http://localhost:8080/movie", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        title: movie_name,
        standard: standard,
        forChildren: children,
        newReleased: newRelease,
      }),
    });
    const data = await response.json().catch((error) => {
      bad_request = true;
    });

    find_movie();
  }

  async function try_new_movie() {
    bad_request = false;
  }

  let movies = [];
  async function find_movie() {
    const response = await fetch(`http://localhost:8080/movie`);
    const data = await response.json();
    movies = data;
  }
  find_movie();
</script>

<div id="rout">
  <MaterialApp>
    {#if !bad_request}
      <div class="container mb-16 d-flex justify-center">
        <Row class="align-{1}" noGutters style="height:150px">
          <Col cols={8} offset={1}>
            <div id="div_text">
              <TextField solo bind:value={movie_name} />
            </div>
          </Col>
          <Col>
            <Radio bind:group value={1}>Standard</Radio>
            <Radio bind:group value={2}>Nuova uscita</Radio>
            <Radio bind:group value={3}>Per bambini</Radio>
          </Col>
        </Row>
      </div>
      <Button class="mb-10" type="button" on:click={create_movie}
        >Post it</Button
      >
      <hr />
    {:else}
    <h5 id="text_bad">BAD REQUEST: errore nell'inserire i dati</h5>
      <Button on:click={try_new_movie}>Riprova</Button>
    {/if}
  </MaterialApp>

  <h5 id="rout" class="mb-10">The Movies:</h5>
  {#if movies.length == 0}
    <p>Your Movie list is empty</p>
  {/if}

  {#if movies.length != 0}
    <MaterialApp>
      <DataTable>
        <DataTableHead>
          <DataTableRow>
            <DataTableCell>Nome</DataTableCell>
            <DataTableCell>Categoria</DataTableCell>
          </DataTableRow>
        </DataTableHead>

        <DataTableBody>
          {#each movies as movie}
            <DataTableRow>
              <DataTableCell>{movie.title}</DataTableCell>
              {#if movie.newReleased}
                <DataTableCell>Ultima uscita</DataTableCell>
              {:else if movie.standard}
                <DataTableCell>Standard</DataTableCell>
              {:else}
                <DataTableCell>Per bambini</DataTableCell>
              {/if}
            </DataTableRow>
          {/each}
        </DataTableBody>
      </DataTable>
    </MaterialApp>
  {/if}
</div>

<style>
  #div_text {
    width: 90%;
  }
  #rout {
    margin-top: 10%;
  }

  #text_bad {
    color: red;
    margin-bottom: 2%;
  }
</style>
