package aplicacion;

import javax.media.opengl.GLProfile;

public class CommonAttributesProvider {

	public static GLProfile getOpenGlDefaultProfile()
	{
		return GLProfile.get(GLProfile.GL2);
	}
}
