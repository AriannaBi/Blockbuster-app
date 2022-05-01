<script>
  import {
    MaterialApp,
    Button,
    Row,
    Col,
    Select,
    TextField,
  } from "svelte-materialify";

  import {
    DataTable,
    DataTableHead,
    DataTableRow,
    DataTableCell,
    DataTableBody,
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

    location.reload()
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
  <Button class="mb-10" on:click={find_user}>Cerca un user</Button>
  <Button class="mb-10" on:click={show_inputs}>Crea un user</Button>

  {#if find_user_label}
    {#if users.length == 0}
      <h6>La lista di user é vuota</h6>
    {/if}
    {#each users as user}
    <div id ="class_list" class="d-flex justify-left">
      <p id="list_user"><a href={`#/user/${user.id}`}> {user.name}</a></p>
    </div>
    {/each}
  {/if}

  {#if show_post_user_label}
    {#if !bad_request}
      <div class="d-flex justify-center">
        {#if !login_label}
          <input
            class="mt-15 elevation-{3} rounded ma-4"
            solo
            placeholder="Scrivi un nome utente"
            bind:value={user_name}
            id="textField_id"
          />
          <Button class="mt-16  ma-4" type="button" on:click={create_user}
            >Crea l'utente</Button
          >
        {/if}
        {#if login_label}
          <a class="mt-15" href={`#/user/${user_id}`}> <h5>Log in as {name_}</h5></a>
        {/if}
      </div>
    {:else}
    <h5 id="text_bad">BAD REQUEST: errore nell'inserire i dati</h5>
      <MaterialApp>
        <Button on:click={try_new_rent}>Riprova</Button>
      </MaterialApp>
    {/if}
  {/if}
</div>

<style>
  #rout {
    margin-top: 7%;
    justify-content: center;
  }

  #class_list {
    width: 50%;
    /* margin-top:5%; */
    margin-left:45%
  }

  #list_user {
    font-size: 120%;
    text-align: left;
  }
  #text_bad {
    color: red;
    margin-bottom: 2%;
  }

  input {
    border: 1px solid #ccc;
    /* border-radius: 10; */
    width: 40%;
  }

  ::placeholder {
    /* Chrome, Firefox, Opera, Safari 10.1+ */
    color: black;
    opacity: 0.5; /* Firefox */
  }
</style>
