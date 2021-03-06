DROP TABLE `Quotes` IF EXISTS;
CREATE TABLE `Quotes` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
	DATE date NOT NULL,
	EXCHANGE VARCHAR(6) NOT NULL,
   `ENTRY_ID` INT NOT NULL,
  `TICKER` varchar(5) NOT NULL,
  `OPEN_PRICE` float NOT NULL,
  `HIGH_PRICE` float NOT NULL,
  `LOW_PRICE` float NOT NULL,
  `CLOSE_PRICE` float NOT NULL,
   VOLUME long NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

GRANT all ON * TO 'christian';
GRANT ALL ON * TO 'qtrend';
