# Meet the Devs

This is a simple repository app for developers' information

# Endpoints

## Get Developers

### Request

`GET /developers/`

### Sample Response

	{
		"id" : "68217921-044d-4dfe-ab11-686b88546242",
		"photo_url" : "https://miro.medium.com/max/750/1*glQKbhzuKTVv3wKCLhjeiQ@2x.jpeg",
		"full_name" : "Evan Hansen",
		"mobile_number" : "+639171234321",
		"email" : "evan@deh.com",
		"company_name" : "Orchard Inc."
	}

## Add Developer

### Request

`POST /developers/`

### Sample Response

	{
		"id" : "68217921-044d-4dfe-ab11-686b88546242",
		"photo_url" : "https://miro.medium.com/max/750/1*glQKbhzuKTVv3wKCLhjeiQ@2x.jpeg",
		"full_name" : "Evan Hansen",
		"mobile_number" : "+639171234321",
		"email" : "evan@deh.com",
		"company_name" : "Orchard Inc."
	}

## Update Developer

### Request

`PUT /developers/{id}`

### Sample Response

	{
		"id" : "68217921-044d-4dfe-ab11-686b88546242",
		"photo_url" : "https://miro.medium.com/max/750/1*glQKbhzuKTVv3wKCLhjeiQ@2x.jpeg",
		"full_name" : "Evan Hansen",
		"mobile_number" : "+639171234321",
		"email" : "evan@deh.com",
		"company_name" : "Orchard Inc."
	}

## Delete Developer

### Request

`DELETE /developers/{id}`

# API URL

This build will connect to the following endpoint for all requests:

	[https://kryzlmeet.free.beeceptor.com/developers/](https://kryzlmeet.free.beeceptor.com/developers/)

# Mocking Responses

You may edit the mock responses here:

	[https://beeceptor.com/console/kryzlmeet](https://beeceptor.com/console/kryzlmeet)

# Native Android Architecture

See design [here](https://github.com/kyrstynuy/meet-the-devs/blob/main/meet-the-devs.png)