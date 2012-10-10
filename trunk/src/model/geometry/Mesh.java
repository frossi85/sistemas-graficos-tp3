package model.geometry;

public class Mesh
{
    public int numOfVerts = 0;
    public int numOfFaces = 0;
    public int numTexCoords = 0;
    public int materialID = 0;
    public boolean hasTexture = false;
    public String name = null;
    public int indices = 0;
    public Vec4 vertices[] = null;
    public Vec4 normals[] = null;
    public TexCoord texCoords[] = null;
    public Face faces[] = null;
    public Bounds bounds = null;
    
    public Mesh() {
        this("default");
    }
    
    public Mesh(String name) {
        this.name = name;
        bounds = new Bounds();
    }
}