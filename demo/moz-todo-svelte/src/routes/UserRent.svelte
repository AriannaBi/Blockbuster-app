<script>
// import { ComboBox } from "carbon-components-svelte";

let rents = [];
async function find_rent() {
   
    let user_id_current_url = document.URL.split("/")[5]
    const response = await fetch(`http://localhost:8080/user/${user_id_current_url}/rent`);
    const data = await response.json();
    rents = data;
    // console.log(data);
}

let movies = [];
let movie_select;
async function fetch_movie() {
    const response = await fetch(`http://localhost:8080/movie`);
    const data = await response.json();
    movies = data;
    console.log("Movie" + data);
}

find_rent();
fetch_movie();

</script>


<p id="rout">The rentals:</p>
<p> - </p>
{#each rents as rent}
    <div>
        <p>Name: {rent.movie.title}</p>
        <p>Start date: {rent.start}</p>
        <p>End date: {rent.end}</p>
        <p> - </p>
    </div>
{/each}


<p>Create a new rental:</p>
<!-- <div><input bind:value={user_name} /></div> -->
<select multiple bind:value={movie_select}>
	{#each movies as movie}
		<option value={movie.title}>{movie.title}
		</option>
	{/each}
</select>


<style>
    #rout {
      margin-top: 8%;
    }
  </style>
