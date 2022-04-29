<script>
  import {
    MaterialApp,
    Button,
    Row,
    Col,
    Select,
    TextField,
  } from "svelte-materialify";
  export let users = [];
  let user_name;
  let show_post_user_label = false;
  let find_user_label = false;
  let login_label = false;

  async function find_user() {
    find_user_label = true;
    show_post_user_label = false;
    const response = await fetch("http://localhost:8080/user");
    const data = await response.json();
    users = data;
  }

  function show_inputs() {
    show_post_user_label = true;
    find_user_label = false;
  }

  let bad_request = false;
  async function create_user() {
    console.log();
    if (document.getElementById("textField_id").value == "") {
      bad_request = true;
    }
    login_label = true;
    find_user_label = false;
    const response = await fetch("http://localhost:8080/user", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ name: user_name }),
    });
    const data = await response.json();
    appear_log_in_with_name();
  }
  async function try_new_rent() {
    bad_request = false;
  }

  let name_;
  let user;
  let user_id;
  async function appear_log_in_with_name() {
    const response = await fetch("http://localhost:8080/user");
    const data = await response.json();
    users = data;

    //iterate over all the data array of users to see which match with the input name
    users.forEach((elem) => {
      if (elem.name == user_name) {
        user = elem;
      }
    });
    user_id = user.id;
    name_ = user.name;
    const res = await fetch(`http://localhost:8080/user/${user_id}`);
    const data2 = await res.json();
  }
</script>

<div id="rout">
  <Button on:click={find_user}>Find the user</Button>
  <Button on:click={show_inputs}>Create the user</Button>

  {#if find_user_label}
    {#if users.length == 0}
      <p>List empty</p>
    {/if}
    <div id="div_table_user">
      <table id="table_users">
        {#each users as user}
          <tr>
            <td><a href={`#/user/${user.id}`}> {user.name}</a></td>
          </tr>
        {/each}
      </table>
    </div>
  {/if}

  {#if show_post_user_label}
    {#if !bad_request}
      <div>
        {#if !login_label}
          <TextField solo bind:value={user_name} id="textField_id" />
          <Button type="button" on:click={create_user}>Post it</Button>
        {/if}
        <br />
        {#if login_label}
          <a href={`#/user/${user_id}`}> Log in as {name_}</a>
        {/if}
      </div>
    {:else}
      <p>BAD REQUEST: errore nell'inserire i dati</p>
      <MaterialApp>
        <Button on:click={try_new_rent}>Riprova</Button>
      </MaterialApp>
    {/if}
  {/if}
</div>

<style>
  #rout {
    margin-top: 20%;
  }

  #table_users {
    font-family: Arial, Helvetica, sans-serif;
    border-collapse: collapse;
    width: 50%;
  }

  #div_table_user {
    display: grid;
    place-items: center;
  }

  #table_users td {
    /* border: 1px solid #ddd; */
    padding: 8px;
  }

  td.svelte-lgv1bk.svelte-lgv1bk {
    border-bottom: 1px black;
  }

  /* #table_users tr:nth-child(even) {
    background-color: #f2f2f2;
  } */

  #table_users tr:hover {
    background-color: #ddd;
  }
</style>
