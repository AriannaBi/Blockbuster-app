    
	import Home from "./routes/Home.svelte"
	import Rent from "./routes/Rent.svelte"
	import User from "./routes/User.svelte"
    import UserRent from "./routes/UserRent.svelte"
	
	export const routes = {
		"/": Home,
		"/rent": Rent,
		"/user": User,
        "/user/:id": UserRent
	}
