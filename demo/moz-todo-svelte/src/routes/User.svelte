
<script>

export let users = [];
let user_name;
let show_post_user = false;

async function find_user() {
    const response = await fetch("http://localhost:8080/user");
      const data = await response.json();
    users = data;
      console.log(data);
}

function show_inputs() {
		show_post_user = true;
}

async function create_user() {
		const response = await fetch("http://localhost:8080/user", {
			method: 'POST', 
			headers: {"Content-Type" : "application/json"},
			body: JSON.stringify({"name" : user_name
			})});
  		const data = await response.json();
		appear_rent_page()
	}

let name_;
let user;
async function appear_rent_page() {
    let user;
    const response = await fetch("http://localhost:8080/user");
    const array_user = await response.json();
    console.log(array_user.id);

    //iterate over all the data array of users to see which match with the input name
    array_user.forEach(elem => {
        if (elem.name == user_name) {
            user = elem;
        }
    });
    let user_id = user.id;
    name_ = user.name;
    const res = await fetch(`http://localhost:8080/user/${user_id}`);
    const data2 = await res.json();
}

async function click_name_user(user_id) {

}
</script>


<button on:click={find_user}> Find the user</button>
<button on:click={show_inputs}> Create the user</button>
	{#each users as user}
    <!-- <div><button on:click={click_name_user(user.id)}>{user.name}</button></div> -->
    <!-- <a href={`#/user/${user.id}`}> {user.name}</a> -->
    <div><a href={`#/user/${user.id}`}> {user.name}</a></div>
	{/each}


   


	{#if show_post_user}
	<div><input bind:value={user_name} />
		<button type="button" on:click={create_user}>Post it</button>
		<button> Login as {name_}</button>
	</div>
	{/if}