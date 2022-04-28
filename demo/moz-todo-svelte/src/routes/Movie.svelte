<script>
  import { MaterialApp, Radio, TextField, Button } from "svelte-materialify";

  let group;
  let standard;
  let children;
  let newRelease;
  let movie_name;
  if (group == 1) {
    standard = true;
    children = false;
    newRelease = false;
  } else if (group == 2) {
    standard = false;
    children = true;
    newRelease = false;
  } else {
    standard = false;
    children = false;
    newRelease = true;
  }

  async function create_movie() {
    const response = await fetch("http://localhost:8080/movie", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ title: movie_name, standard : standard, forChildren: children,newReleased: newRelease  }),
    });
    const data = await response.json();
  }
</script>

<div id="rout">
  <MaterialApp>
    <TextField solo bind:value={movie_name} />

    <Radio bind:group value={1}>Standard</Radio>
    <Radio bind:group value={2}>Nuova uscita</Radio>
    <Radio bind:group value={3}>Per bambini</Radio>
    <Button type="button" on:click={create_movie}>Post it</Button>
  </MaterialApp>
</div>

<style>
  #rout {
    margin-top: 20%;
  }
</style>
