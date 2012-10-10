package model.geometry;

import java.util.Vector;

public class Model
{
    protected Vector<Material> materials = new Vector<Material>();
    protected Vector<Mesh> mesh = new Vector<Mesh>();
    protected String source;
    
    protected boolean renderModel = true;
    protected boolean centerModel = false;
    protected boolean renderModelBounds = false;
    protected boolean renderObjectBounds = false;
    protected boolean unitizeSize = true;
    protected boolean useTexture = true;
    protected boolean renderAsWireframe = false;
    protected boolean useLighting = true;
    
    /** Bounds of the model */
    protected Bounds bounds = new Bounds();
    /** Center point of the model */
    private Vec4 centerPoint = new Vec4(0.0f, 0.0f, 0.0f);

    // Constructor
    public Model(String source)
    {
        this.source = source;
    }

    // Add material
    public void addMaterial(Material mat)
    {
        materials.add(mat);
    }

    // Add a mesh
    public void addMesh(Mesh obj)
    {
        mesh.add(obj);
    }

    // Get material
    public Material getMaterial(int index)
    {
        return materials.get(index);
    }

    // Get a a mesh
    public Mesh getMesh(int index)
    {
        return mesh.get(index);
    }

    // Get the number of meshes
    public int getNumberOfMeshes()
    {
        return mesh.size();
    }

    // Get the number of materials
    public int getNumberOfMaterials()
    {
        return materials.size();
    }

    public String getSource() {
        return source;
    }

    public boolean isRenderModel() {
        return renderModel;
    }

    public void setRenderModel(boolean renderModel) {
        this.renderModel = renderModel;
    }

    public boolean isCentered() {
        return centerModel;
    }

    public void centerModelOnPosition(boolean centerModel) {
        this.centerModel = centerModel;
    }

    public boolean isRenderModelBounds() {
        return renderModelBounds;
    }

    public void setRenderModelBounds(boolean renderModelBounds) {
        this.renderModelBounds = renderModelBounds;
    }

    public boolean isRenderObjectBounds() {
        return renderObjectBounds;
    }

    public void setRenderObjectBounds(boolean renderObjectBounds) {
        this.renderObjectBounds = renderObjectBounds;
    }
    
    /** 
     * Returns the bounds of the model 
     *
     * @return Bounds of the model 
     */
    public Bounds getBounds() {
        return bounds;
    }
    
    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }
    
    /** 
     * Returns the center of the model bounds
     *
     * @return Center of the model
     */
    public Vec4 getCenterPoint() {
        return this.centerPoint;
    }

    public void setCenterPoint(Vec4 center) {
        this.centerPoint = center;
    }

    public boolean isUnitizeSize() {
        return unitizeSize;
    }

    public void setUnitizeSize(boolean unitizeSize) {
        this.unitizeSize = unitizeSize;
    }

    public boolean isUsingTexture() {
        return useTexture;
    }

    public void setUseTexture(boolean useTexture) {
        this.useTexture = useTexture;
    }

    public boolean isRenderingAsWireframe() {
        return renderAsWireframe;
    }

    public void setRenderAsWireframe(boolean renderAsWireframe) {
        this.renderAsWireframe = renderAsWireframe;
    }

    public boolean isUsingLighting() {
        return useLighting;
    }

    public void setUseLighting(boolean useLighting) {
        this.useLighting = useLighting;
    }
}
