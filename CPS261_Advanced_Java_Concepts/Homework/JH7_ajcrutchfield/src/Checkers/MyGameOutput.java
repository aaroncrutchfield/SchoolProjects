package Checkers;

import java.io.Serializable;


public class MyGameOutput  implements Serializable
{    
	MyGame myGame=null;

	public MyGameOutput(MyGame mg)
	{
		myGame =mg;
	}

}
