package engine.graph;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import org.lwjgl.system.MemoryUtil;

public class Mesh {

    private final int mVaoId;

    private final int mPostVboId;

    private final int mColourVboId;

    private final int mIdxVboId;

    private final int mVertexCount;

    public Mesh(float[] positions, float[] colours, int[] indices) {
        FloatBuffer posBuffer = null;
        FloatBuffer colourBuffer = null;
        IntBuffer indicesBuffer = null;
        try {
            mVertexCount = indices.length;

            mVaoId = glGenVertexArrays();
            glBindVertexArray(mVaoId);

            // Position VBO
            mPostVboId = glGenBuffers();
            posBuffer = MemoryUtil.memAllocFloat(positions.length);
            posBuffer.put(positions).flip();
            glBindBuffer(GL_ARRAY_BUFFER, mPostVboId);
            glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

            // Colour VBO
            mColourVboId = glGenBuffers();
            colourBuffer = MemoryUtil.memAllocFloat(colours.length);
            colourBuffer.put(colours).flip();
            glBindBuffer(GL_ARRAY_BUFFER, mColourVboId);
            glBufferData(GL_ARRAY_BUFFER, colourBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);

            // Index VBO
            mIdxVboId = glGenBuffers();
            indicesBuffer = MemoryUtil.memAllocInt(indices.length);
            indicesBuffer.put(indices).flip();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mIdxVboId);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        } finally {
            if (posBuffer != null) {
                MemoryUtil.memFree(posBuffer);
            }
            if (colourBuffer != null) {
                MemoryUtil.memFree(colourBuffer);
            }
            if (indicesBuffer != null) {
                MemoryUtil.memFree(indicesBuffer);
            }
        }
    }

    public int getVaoId() {
        return mVaoId;
    }

    public int getVertexCount() {
        return mVertexCount;
    }

    public void render() {
        // Draw the mesh
        glBindVertexArray(getVaoId());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0);

        // Restore state
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
    }

    public void cleanUp() {
        glDisableVertexAttribArray(0);

        // Delete the VBOs
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(mPostVboId);
        glDeleteBuffers(mColourVboId);
        glDeleteBuffers(mIdxVboId);

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(mVaoId);
    }
}