# HR-Management-System
### Description-
HR Management-System,It is a desktop application based on Layered Architecture to manage and manipulate information of employees.<br/> The entire application is developed in three phases as ***DATA LAYER, BUSINESS LAYER, PRESENTATION LAYER***. <br/> 
*The first one*<br/>
**DATA Layer:** DL is developed in two phases or part.<br/>
- In a first phase data layer based on the concept of File Handling to store the data in a file.<br/>
- In a second phase, after developing the all three layers, the data layer is changed separately to store the data in database where MYSQL database is used.This is done without doing any changes in *BL* and *PL*.<br/>
- In the both part DTO is used to carries data and DAO design pattern is used to isolate the busines-layer from data-layer. 

**Busines Layer:**<br/>
- POJO and managers are used in business layer to isolate it from presentation-layer.<br/>
- Data Structures are design to store the data and records at the run time which is fetched from data-layer while populating data-structures.<br/>
- singleton Pattern is followed to populate data-structures at business-layer.<br/>

- **Presentation Layer:** It is basically based on the concept of *model* and *view*.
- Model: fetching the data and manipulating data - *Data Population* is done in model.
- View: Java Swing and awt is used to design the user-interface.
