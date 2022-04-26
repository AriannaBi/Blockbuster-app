<script>
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
    // console.log(data);
  }

  function show_inputs() {
    show_post_user_label = true;
    find_user_label = false;
  }

  async function create_user() {
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

<button on:click={find_user} id="rout"> Find the user</button>
<button on:click={show_inputs} id="rout"> Create the user</button>
{#if find_user_label}
  {#each users as user}
    <div><a href={`#/user/${user.id}`}> {user.name}</a></div>
  {/each}
{/if}

{#if show_post_user_label}
  <div>
    <!-- {#if !login_label} -->
      <input bind:value={user_name} />
      <button type="button" on:click={create_user}>Post it</button>
    <!-- {/if} -->
    <br />
    <!-- {#if login_label} -->
      <a href={`#/user/${user_id}`}> {name_}</a>
    <!-- {/if} -->
  </div>
{/if}

<style>
  #rout {
    margin-top: 5%;
  }
</style>
