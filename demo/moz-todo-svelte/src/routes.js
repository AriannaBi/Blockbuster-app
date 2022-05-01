    
	import Home from "./routes/Home.svelte"
	import User from "./routes/User.svelte"
    import UserRent from "./routes/UserRent.svelte"
    import Movie from "./routes/Movie.svelte"
	
	export const routes = {
		"/": Home,
		"/user": User,
        "/user/:id": UserRent,
        "/movie": Movie
	}
