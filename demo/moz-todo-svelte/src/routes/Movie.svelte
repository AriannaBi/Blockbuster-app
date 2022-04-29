<script>
  import { MaterialApp, Radio, TextField, Button } from "svelte-materialify";
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
    } else if (group == 3){
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
  {#if !bad_request}
    <MaterialApp>
      <TextField solo bind:value={movie_name} />

      <Radio bind:group value={1}>Standard</Radio>
      <Radio bind:group value={2}>Nuova uscita</Radio>
      <Radio bind:group value={3}>Per bambini</Radio>
      <Button type="button" on:click={create_movie}>Post it</Button>
    </MaterialApp>
  {:else}
    <p>BAD REQUEST: errore nell'inserire i dati</p>
    <MaterialApp>
      <Button on:click={try_new_movie}>Riprova</Button>
    </MaterialApp>
  {/if}

  <p id="rout">The Movies:</p>
  {#if movies.length == 0}
    <p>Your Movie list is empty</p>
  {/if}

  {#if movies.length != 0}
    <div id="div_table_rent">
      <table id="table_rent">
        <tr>
          <th>Nome</th>
          <th>Categoria</th>
        </tr>
        {#each movies as movie}
          <tr>
            <td><p>{movie.title}</p></td>
            {#if movie.newReleased}
              <td><p>Ultima Uscita</p></td>
            {:else if movie.standard}
              <td><p>Standard</p></td>
            {:else}
              <td><p>Per bambini</p></td>
            {/if}
          </tr>
        {/each}
      </table>
    </div>
  {/if}
</div>

<style>
  #rout {
    margin-top: 10%;
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
