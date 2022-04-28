import App from './App.svelte';

const app = new App({
	target: document.body,
	props: {
		name: 'Blockbuster'
		// find_user_visible: false,
		// create_user_visible: false
	}
});

export default app;