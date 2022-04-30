<script>
  import {
    DataTable,
    DataTableHead,
    DataTableRow,
    DataTableCell,
    DataTableBody,
    MaterialApp,
  } from "svelte-materialify";
  let array_users = [];

  async function find_user() {
    const res2 = await fetch(`http://localhost:8080/user`);
    let user_info = await res2.json();
    array_users = [];

    user_info.forEach((elem) => {
      array_users.push(elem);
    });
  }

  function reformat_date(date) {
    var day = date[2];
    date[2] = date[0];
    date[0] = day;
    date = `${date[0]}.${date[1]}.${date[2]}`;
    return date;
  }

  find_user();
</script>

<h4 class="mb-10" id="rout">Overview</h4>
<MaterialApp>
  <DataTable class="d-flex flex-column">
    <DataTableHead>
      <DataTableRow>
        <DataTableCell>Nome user</DataTableCell>
        <DataTableCell>Fidelity</DataTableCell>
      <!-- <DataTableRow> -->
        <DataTableCell>Nome Film</DataTableCell>
        <DataTableCell>Data inizio</DataTableCell>
        <DataTableCell>Data fine</DataTableCell>
        <DataTableCell>Riconsegnato</DataTableCell>
        <DataTableCell>Prezzo pagato</DataTableCell>
        <DataTableCell>Deposito da restituire</DataTableCell>
      <!-- </DataTableRow> -->
      </DataTableRow>
    </DataTableHead>

    <DataTableBody>
      {#each array_users as user}
          {#each user.rentals as rent}
              <DataTableRow>
                <DataTableCell>{user.name}</DataTableCell>
          {#if user.lostBeforeFidelity}
            <DataTableCell>No</DataTableCell>
          {:else}
            <DataTableCell>Si</DataTableCell>
          {/if}
                <DataTableCell>{rent.movie.title}</DataTableCell>
              <DataTableCell>{reformat_date(rent.start)}</DataTableCell>
              {#if rent.actualEnd == null}
                <DataTableCell>{reformat_date(rent.end)}</DataTableCell>
                <DataTableCell>No</DataTableCell>
              {:else}
                <DataTableCell>{reformat_date(rent.actualEnd)}</DataTableCell>
                <DataTableCell>Si</DataTableCell>
              {/if}
              <DataTableCell>{rent.price}</DataTableCell>
              <DataTableCell>{rent.deposit}</DataTableCell>
            </DataTableRow>
          {/each} 
      {/each}
    </DataTableBody>
  </DataTable>
</MaterialApp>

<style>
  #rout {
    margin-top: 10%;
  }
  #table_rent {
    font-family: Arial, Helvetica, sans-serif;
    border-collapse: collapse;
    /* width: 70%; */
  }

  #div_table_rent {
    display: grid;
    place-items: center;
  }

  #table_rent td {
    border: 1px solid #ddd;
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
