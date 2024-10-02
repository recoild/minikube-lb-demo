<h1>minikube 로드밸런싱 실습</h1>

<img src="./images/demo.gif"/>

<h2>1. Docker 빌드 및 Docker Hub로 Push</h2>
<ol>
    <li>먼저 Docker가 설치되고 실행 중인지 확인합니다.</li>
    <li>아래 명령어로 Docker 이미지를 빌드합니다:
        <pre><code>docker build -t 당신의-dockerhub-아이디/helloworld-app .</code></pre>
    </li>
    <li>이미지가 빌드된 후, Docker Hub에 로그인합니다:
        <pre><code>docker login</code></pre>
    </li>
    <li>Docker 이미지를 Docker Hub에 푸시합니다:
        <pre><code>docker push 당신의-dockerhub-아이디/helloworld-app</code></pre>
    </li>
</ol>

<h2>2. Minikube에 배포</h2>
<p>Minikube가 설치되고 실행 중인지 확인합니다. Minikube를 시작하려면 다음 명령어를 사용하세요:</p>
<pre><code>minikube start</code></pre>

<h3>2.1 디플로이먼트 생성</h3>
<p>Minikube에 디플로이먼트를 생성하기 위해 <code>images</code> 폴더에 제공된 <code>deployment.yml</code> 파일을 사용할 수 있습니다. 예시는 다음과 같습니다:</p>
<pre><code>
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp
spec:
  replicas: 3
  selector:
    matchLabels:
      app: myapp
  template:
    metadata:
      labels:
        app: myapp
    spec:
      containers:
      - name: myapp
        image: cajhyung/myapp:0.1
        resources:
          limits:
            memory: "128Mi"
            cpu: "500m"
        ports:
        - containerPort: 8080
</code></pre>
<p>다음 명령어로 디플로이먼트를 적용합니다:</p>
<pre><code>kubectl apply -f images/deployment.yml</code></pre>

<h2>3. 로드밸런서 서비스 생성</h2>
<p>서비스를 생성하기 위해 <code>service.yml</code> 파일을 사용합니다. 예시는 다음과 같습니다:</p>
<pre><code>
apiVersion: v1
kind: Service
metadata:
  name: myapp
spec:
  selector:
    app: myapp
  ports:
  - port: 80
    targetPort: 8080
  type: LoadBalancer
</code></pre>
<p>서비스를 적용하려면 아래 명령어를 사용하세요:</p>
<pre><code>kubectl apply -f images/service.yml</code></pre>
<p>이제 Minikube에서 할당된 IP와 포트를 통해 애플리케이션에 접근할 수 있습니다. 할당된 IP를 확인하려면 아래 명령어를 사용하세요:</p>
<pre><code>minikube service helloworld-service</code></pre>
