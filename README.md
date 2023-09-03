<h1 align="center">Clever-Bank</h1>
<h3 align="center">Консольное приложение / Console application</h3>

<h4>Стек технологий / Technology stack:</h4>
<li>Java 17</li>
<li>Gradle</li>
<li>PostgreSQL</li>
<li>SnakeYAML</li>
<li>ModelMapper</li>
<li>Log4j2</li>
<li>Junit</li>
<li>Mockito</li>

<h4>Цели проекта / Project objectives:</h4>
<p>Перед Вами консольное приложение, которое позволит совершать различные банковские операции.</p>
<p>Доступны следующие операции:</p>
<li>снятия средств с выбранного счета </li>
<li>пополнения средств на выбранный счет </li> 
<li>перевод средств со счета на счет* </li>
<p>* Есть возможность осуществлять переводы между своими счетами, на счета других пользователей банка, а также на счета пользователям других банков.</p>
<p>При осуществлении операций перевода средств, как между своими счетами, так и другому клиенту обеспечена безопасность и используется одна транзакция.</p>
<p>Работа с базой данных осуществляется в многопоточной среде с использованием пула соединений.</p>
<p>Реализован планировщик заданий, который работает асинхронно и раз в 30 секунд проверяет, необходимо ли начислять проценты на остаток по счету в зависимости от даты сервисного обслуживания счета.</p>
<p>По завершению каждой операции, формируется чек и сохраняется в формате TXT.</p>

<p>В коде присутствует документация формате JavaDoc на каждом методе.</p>
<p>Реализованы ведение журнала логирования информации приложения.</p>

<p>Для запуска приложения** Вам необходимо загрузить файлы из этого репозитория на свой компьютер. Если Вы используете Intellij Idea, Вы можете скачать данный проект через создание нового проекта с системой контроля версий по следующей ссылке «https://github.com/NortinPowers/CleverBankConsoleApp.git». Далее, необходимо в меню Task/Build сборщика Gradle собрать проект командой «build» и запустить метод main класса Main.</p>

<p>**Перед запуском необходимо:</p>
<li>настроить соединение с базой данных PostgreSQL ***</li>
<li>инициализировать тестовые базы данных ****</li>

<p>*** Для настройки соединение переопределите настройки «database» в файле конфигурационном файле «resources/ application.yaml» на используемые Вами.</p>
<p>**** Для формирования и заполнения тестовой базы данных, необходимо воспользоваться .sql файлами («init_table.sql» и «fill_table.sql») находящимися в разделе «resources/db/..»</p>

<p>Операции с базой данных.</p>
<p>При отсутствии графического интерфейса, просмотреть информацию в базах данных можно используя следующие запросы:</p>
<p>Просмотреть информацию по счетам для Clever-Bank:</p>

<li>select cba.id, balance, u.login, number, c.code from clever_bank_accounts cba
<p>inner join public.users u on u.id = cba.user_id
<p>inner join public.currencies c on c.id = cba.currency_id</li>

<p>Аналогично для остальных банков:</p>

<li>select bba.id, balance, u.login, number, c.code from beta_bank_accounts bba
<p>inner join public.users u on u.id = bba.user_id
<p>inner join public.currencies c on c.id = bba.currency_id</li>

<li>select gba.id, balance, u.login, number, c.code from gamma_bank_accounts gba
<p>inner join public.users u on u.id = gba.user_id
<p>inner join public.currencies c on c.id = gba.currency_id</li>

<li>select sba.id, balance, u.login, number, c.code from sigma_bank_accounts sba
<p>inner join public.users u on u.id = sba.user_id
<p>inner join public.currencies c on c.id = sba.currency_id</li>

<li>select zba.id, balance, u.login, number, c.code from zeta_bank_accounts zba
<p>inner join public.users u on u.id = zba.user_id
<p>inner join public.currencies c on c.id = zba.currency_id</li>

<p>Для просмотра осуществленных транзакций:</p>

<li>select t.id, t.date, t.monies, sb.name as sending_bank , rb.name as recipient_bank, c.code, ot.type from transactions t
<p>inner join public.banks sb on sb.id = t.sending_bank_id
<p>inner join public.banks rb on rb.id = t.recipient_bank_id
<p>inner join public.currencies c on c.id = t.currency_id
<p>inner join public.operation_types ot on ot.id = t.operation_type_id</li>

<p>Для просмотра таблицы пользователей:</p>

<li>select id, login from users</li>

<p>/</p>
<p>Here is a console application that will allow you to perform various banking operations.</p>
<p>The following operations are available:</p>
<li>withdrawals from the selected account </li>
<li>deposits to the selected account </li> 
<li>transfer of funds from account to account* </li>
<p>* It is possible to make transfers between your accounts, to the accounts of other bank users, as well as to the accounts of users of other banks.</p>
<p>When performing money transfer operations, both between their accounts and to another client, security is ensured and one transaction is used.</p>
<p>Working with the database is carried out in a multithreaded environment using a connection pool.</p>
<p>A task scheduler has been implemented that works asynchronously and checks every 30 seconds whether it is necessary to charge interest on the account balance depending on the date of the account service.</p>
<p>Upon completion of each operation, a receipt is generated and saved in TXT format.</p>
<p>The code contains documentation in JavaDoc format for each method.</p>
<p>Logging of application information logging is implemented.</p>

<p>To run the application, you need to download files from this repository to your computer. If you use Intellij Idea, you can download this project by creating a new project with a version control system at the following link "https://github.com/NortinPowers/CleverBankConsoleApp.git ". Next, you need to assemble the project with the "build" command in the Task/Build menu of the Gradle build tool and run the main method of the Main class.</p>
<p>Before starting, you need to:</p>
<li>configure connection to PostgreSQL database **</li>
<li>initialize test databases ***</li>

<p>** To configure the connection, redefine the "database" settings in the "resources/ application.yaml" configuration file to the ones you use.</p>
<p>*** To form and fill the test database, you need to use .sql files ("init_table.sql" and "fill_table.sql") located in the "resources/db/.." section</p>

<p>Database operations.</p>
<p>In the absence of a graphical interface, you can view information in databases using the following queries:</p>
<p>View account information for Clever-Bank:</p>

<li>select cba.id, balance, u.login, number, c.code from clever_bank_accounts cba
<p>inner join public.users u on u.id = cba.user_id
<p>inner join public.currencies c on c.id = cba.currency_id</li>

<p>Similar for other banks:</p>

<li>select bba.id, balance, u.login, number, c.code from beta_bank_accounts bba
<p>inner join public.users u on u.id = bba.user_id
<p>inner join public.currencies c on c.id = bba.currency_id</li>

<li>select gba.id, balance, u.login, number, c.code from gamma_bank_accounts gba
<p>inner join public.users u on u.id = gba.user_id
<p>inner join public.currencies c on c.id = gba.currency_id</li>

<li>select sba.id, balance, u.login, number, c.code from sigma_bank_accounts sba
<p>inner join public.users u on u.id = sba.user_id
<p>inner join public.currencies c on c.id = sba.currency_id</li>

<li>select zba.id, balance, u.login, number, c.code from zeta_bank_accounts zba
<p>inner join public.users u on u.id = zba.user_id
<p>inner join public.currencies c on c.id = zba.currency_id</li>

<p>To view completed transactions:</p>

<li>select t.id, t.date, t.monies, sb.name as sending_bank , rb.name as recipient_bank, c.code, ot.type from transactions t
<p>inner join public.banks sb on sb.id = t.sending_bank_id
<p>inner join public.banks rb on rb.id = t.recipient_bank_id
<p>inner join public.currencies c on c.id = t.currency_id
<p>inner join public.operation_types ot on ot.id = t.operation_type_id</li>

<p>To view the user table:</p>

<li>select id, login from users</li>


<h4>Контакты / Contact:</h4>
<p>a.nortin@gmail.com</p>