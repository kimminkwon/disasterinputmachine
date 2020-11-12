<h1> Disaster DB Input Machine <sub>for Korea Disaster Research Center</sub><h1>

<h3> Program </h3>

<p> 
    This program is database input machine consisted of Spring Boot of Disaster Data for Research of Korea Disaster Research Center. 
</p>

<h3> Dependency </h3>

<p> The program was built using Spring Boot </p>
<ul>
    <p>
        <li> Dependency for Program </li>
        <ul>
            <li> Spring Web, Spring Boot </li>
            <li> Thymeleaf, Thymeleaf-layout </li>
            <li> Spring Security </li>
            <li> Querydsl </li>
            <li> MySQL </li>
            <li> Bootstrap </li>
            <li> Gradle </li>
            <li> Poi, Poi-ooxml </li>
            <li> commons-io </li>
        </ul>
    </p>
    <p>
        <li> Dependency for Test </li>
        <ul>
           <li> junit </li>
           <li> SpringBootTest </li>
        </ul>
    </p>
</ul>
  
<h3> Program Infomation </h3>

<ul> 
    <p>
        <li> Repository </li>
        <ul>
            <li> Using MySQL </li>
            <li> Using CrudRepository Interface & Querydsl for condition </li>
            <li> Have 3 type Repository: Maintenance, Reservation, Delete </li>
        </ul>
    </p>
    <p>
        <li> Views for each repository </li>
        <ul>
            <li> list: showing data list </li>
            <li> view: detail view for some data </li>
            <li> modify: modify some data </li>
            <li> register: add data </li>
            <li> listforonce: showing data list of choices </li>
            <li> search: combined searching page </li>            
        </ul>
    </p>
    <p>
        <li> Diagram for each Layer </li>
        <div>
            <img width="250" height="150" src="https://user-images.githubusercontent.com/51231789/98075220-5343e280-1eaf-11eb-820a-608f89c7a41c.png" />
        </div>
    </p>
    <p>
        <li> Diagram for each job </li>
        <ul>
            <li> Show all list </li>
            <div>
                <img src="https://user-images.githubusercontent.com/51231789/97132558-ad93c380-178a-11eb-8392-99dd4dbea0e8.png" />
            </div>
            <li> View for Detail infomation of one entity </li>
            <div>
                <img src="https://user-images.githubusercontent.com/51231789/97132657-fc415d80-178a-11eb-8679-c342522a731c.png" />
            </div>
            <li> Resister </li>
            <div>
                <img src="https://user-images.githubusercontent.com/51231789/97132556-acfb2d00-178a-11eb-8488-ae63c93928d0.png" />
            </div>  
            <li> Delete </li>
            <div>
                <img src="https://user-images.githubusercontent.com/51231789/97132557-acfb2d00-178a-11eb-96f4-e0aa95b27d59.png" />
            </div>  
            <li> Modify </li>
            <div>
                <img src="https://user-images.githubusercontent.com/51231789/97132552-abca0000-178a-11eb-9ea0-f37a35eec25a.png" />
            </div> 
        </ul>
    </p>
</ul>

<h3> Goal of development </h3>
<ul>
    <li>Practice Spring boot</li>
    <li>Satisfy all requirements of Korea Disaster Research Center</li>
    <li>Program constraints</li>
        <ul>
        <li> Make Unit test for each unit function </li>
        <li> Do commit per unit </li>
        <li> The depth of the code does not exceed 3 </li>
        </ul>
</ul>

<h3> References </h3>
<p> <i> The program was made by referring to this. </i> </p>
<ul>
    <li> 구멍가게 코딩단, 초급 개발자들을 위한 가볍고 넓은 스타트 스프링 부트, 남가람북스, 2019. </li>
    <li> 이동욱, 스프링 부트와 AWS로 혼자 구현하는 웹 서비스, 프리렉, 2019. </li>
</ul>
