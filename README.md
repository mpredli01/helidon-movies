# Helidon Movies MicroProfile Example

This example is built upon the [Helidon Quickstart MP](https://helidon.io/docs/latest/#/guides/03_quickstart-mp) guide.
It maintains a list of Quentin Tarantino movies and is based off a similar example by [Hantsy Bai](https://cn.linkedin.com/in/hantsy), self-employed technical consultant, solution architect and full-stack developer.  

### Prerequisites

1. Maven 3.5 or newer
2. Java SE 8 or newer
3. Docker 17 or newer (if you want to build and run docker images)
4. Kubernetes minikube v0.24 or newer (if you want to deploy to Kubernetes)
   or access to a Kubernetes 1.7.4 or newer cluster
5. Kubectl 1.7.4 or newer for deploying to Kubernetes

### Verify prerequisites

```
$ java -version
$ mvn --version
$ docker --version
$ minikube version
$ kubectl version --short
```

### Build the project

```
$ mvn clean package
```

### Start the application

```
$ java -jar target/helidon-movies.jar
```

### Exercise the movies application

#### Retrieve the list of Quentin Tarantino movies

```
$ curl -X GET http://localhost:8080/movies/
[{"id":1,"title":"Reservoir Dogs","year":1992},{"id":2,"title":"Pulp Fiction","year":1994},{"id":3,"title":"Jackie Brown","year":1997},{"id":4,"title":"Kill Bill, Volume 1","year":2003},{"id":5,"title":"Kill Bill, Volume 2","year":2004},{"id":6,"title":"Inglourious Basterds","year":2009},{"id":7,"title":"Django Unchained","year":2012},{"id":8,"title":"The Hateful Eight","year":2015},{"id":9,"title":"Once Unpon a Time...in Hollywood","year":2019}]
```

#### Retrieve the second Quentin Tarantino movie

```
$ curl -X GET http://localhost:8080/movies/2
{"id":2,"title":"Pulp Fiction","year":1994}
```

#### Attempt to retrieve a Quentin Tarantino movie in which the ID is not in the list

```
$ curl -X GET http://localhost:8080/movies/19
movieId 19 was not found!

$ curl -X GET http://localhost:8080/movies/-1
movieId -1 was not found!
```

#### Add a new Quentin Tarantino movie to the list

```
$ curl -X POST http://localhost:8080/movies -d "{\"id\" : 10, \"title\" : \"Kill Bill, Volume 3\", \"year\" : 2021}" -H "Content-Type:application/json"
```

#### Retrieve the updated list

```
$ curl -X GET http://localhost:8080/movies/
[{"id":1,"title":"Reservoir Dogs","year":1992},{"id":2,"title":"Pulp Fiction","year":1994},{"id":3,"title":"Jackie Brown","year":1997},{"id":4,"title":"Kill Bill, Volume 1","year":2003},{"id":5,"title":"Kill Bill, Volume 2","year":2004},{"id":6,"title":"Inglourious Basterds","year":2009},{"id":7,"title":"Django Unchained","year":2012},{"id":8,"title":"The Hateful Eight","year":2015},{"id":9,"title":"Once Unpon a Time...in Hollywood","year":2019},{"id":10,"title":"Kill Bill, Volume 3","year":2021}]
```

#### Delete a Quentin Tarantino movie from the list
```
$ curl -X DELETE http://localhost:8080/movies/9
```

#### Retrieve the updated list

```
$ curl -X GET http://localhost:8080/movies/
[{"id":1,"title":"Reservoir Dogs","year":1992},{"id":2,"title":"Pulp Fiction","year":1994},{"id":3,"title":"Jackie Brown","year":1997},{"id":4,"title":"Kill Bill, Volume 1","year":2003},{"id":5,"title":"Kill Bill, Volume 2","year":2004},{"id":6,"title":"Inglourious Basterds","year":2009},{"id":7,"title":"Django Unchained","year":2012},{"id":8,"title":"The Hateful Eight","year":2015},{"id":10,"title":"Kill Bill, Volume 3","year":2021}]
```

### Exercise the original greeting application 

```
$ curl -X GET http://localhost:8080/greet
{"message":"Hello World!"}


$ curl -X GET http://localhost:8080/greet/Mike
{"message":"Hello Mike!"}
```

#### Change the salutation from "Hello" to "Hola"

```
$ curl -X PUT -H "Content-Type: application/json" -d '{"greeting" : "Hola"}' http://localhost:8080/greet/greeting
```

#### Re-execute the greeting application

```
$ curl -X GET http://localhost:8080/greet/Mike
{"message":"Hola Mike!"}
```

### Built-in MicroProfile Health Check

```
$ curl -s -X GET http://localhost:8080/health
{"outcome":"UP","status":"UP","checks":[{"name":"deadlock","state":"UP","status":"UP"},{"name":"diskSpace","state":"UP","status":"UP","data":{"free":"13.52 GB","freeBytes":14514778112,"percentFree":"5.79%","total":"233.57 GB","totalBytes":250790436864}},{"name":"heapMemory","state":"UP","status":"UP","data":{"free":"133.00 MB","freeBytes":139462136,"max":"1.78 GB","maxBytes":1908932608,"percentFree":"97.78%","total":"173.50 MB","totalBytes":181927936}}]}
```

### Built-in MicroProfile Metrics
#### Prometheus Format

```
$ curl -s -X GET http://localhost:8080/metrics
# TYPE base:classloader_current_loaded_class_count counter
# HELP base:classloader_current_loaded_class_count Displays the number of classes that are currently loaded in the Java virtual machine.
base:classloader_current_loaded_class_count 7423
# TYPE base:classloader_total_loaded_class_count counter
# HELP base:classloader_total_loaded_class_count Displays the total number of classes that have been loaded since the Java virtual machine has started execution.
base:classloader_total_loaded_class_count 7426
# TYPE base:classloader_total_unloaded_class_count counter
# HELP base:classloader_total_unloaded_class_count Displays the total number of classes unloaded since the Java virtual machine has started execution.
base:classloader_total_unloaded_class_count 3
...
```

#### JSON Format

```
$ curl -H 'Accept: application/json' -X GET http://localhost:8080/metrics
{"base":{"classloader.currentLoadedClass.count":7433,"classloader.totalLoadedClass.count":7436,"classloader.totalUnloadedClass.count":3,"cpu.availableProcessors":8,"cpu.systemLoadAverage":2.31787109375,"gc.PS MarkSweep.count":2,"gc.PS MarkSweep.time":77,"gc.PS Scavenge.count":8,"gc.PS Scavenge.time":37,"jvm.uptime":195329,"memory.committedHeap":181927936,"memory.maxHeap":1908932608,"memory.usedHeap":49878144,"thread.count":75,"thread.daemon.count":70,"thread.max.count":82},"vendor":{"grpc.requests.count":0,"grpc.requests.meter":{"count":0,"meanRate":0.0,"oneMinRate":0.0,"fiveMinRate":0.0,"fifteenMinRate":0.0},"requests.count":2,"requests.meter":{"count":2,"meanRate":0.010388620245951366,"oneMinRate":0.0069497108324461715,"fiveMinRate":0.0027982224558022787,"fifteenMinRate":0.001048151847790129}}}
```


### Build the Docker image

```
$ docker build -t helidon-movies .
```

### Start the application with Docker

```
$ docker run --rm -p 8080:8080 helidon-movies:latest
```

### While the application is running in Docker, deploy the application to Kubernetes

```
$ kubectl cluster-info                     # verify which cluster
$ kubectl get pods                         # verify connectivity to cluster
$ kubectl create -f app.yaml               # deploy application
$ kubectl get service helidon-movies       # verify deployed service
```
