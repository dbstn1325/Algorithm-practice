### [programmers-Summer/Winter Coding] 스킬트리

<img width="1174" alt="image" src="https://github.com/dbstn1325/Algorithm-practice/assets/78465062/a0dadaf4-f806-483f-8e70-f4e85ffab75d">

### 도식화

1. 스킬 트리를 제외한, 다른 문자 제거 <br>
스킬의 순서만 중요하므로, 판단해야하는 것만을 남기고자 했다.
`(BCD).indexOf`를 통해 스킬 트리와 다른 문자를 제거하는 연산을 거쳤다.
   
2. 스킬트리의 순서에 만족하는지 확인 <br>
C->B->D 라는 순서를 만족한다는 건 `C CB CBD` 즉, CBD 내 시작하는 문자열에 속하는지 확인할 필요가 있다고 생각했다.
`.startsWith`를 통해 조건(스킬트리의 순서)을 만족하는지 확인하고, 만족할 때만 별도 리스트에 넣어주었다.

3. 만족하는 갯수 세기 <br>