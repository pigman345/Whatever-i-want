package io.github.some_example_name;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy {
    public int Ex;
    public int Ey;
    public int Ew;
    public int Eh;
    public int Ecol;
    public int Erow;
    public Color Ecolor;

    private Projectile projectile;

    // Configurable chances for projectile types (can be changed at runtime)
    public static float FIRE_CHANCE = 0.4f;
    public static float ICE_CHANCE = 0.35f;
    public static float SPARK_CHANCE = 0.25f;

    private static final Random RNG = new Random();

    public enum ProjectileType { FIRE, ICE, SPARK }

    public static class Particle {
        float x, y, vx, vy, life, maxLife;
        Color color;

        Particle(float x, float y, float vx, float vy, float life, Color color) {
            this.x = x; this.y = y; this.vx = vx; this.vy = vy; this.life = life; this.maxLife = life; this.color = color;
        }

        void update() {
            x += vx;
            y += vy;
            life -= 1f;
        }

        void draw(ShapeRenderer sr) {
            float alpha = Math.max(0f, life / maxLife);
            Color c = new Color(color.r, color.g, color.b, alpha);
            sr.setColor(c);
            sr.circle(x, y, Math.max(1f, alpha * 3f));
        }
    }

    public static class Projectile {
        public float x;
        public float y;
        public float w;
        public float h;
        public float vx;
        public float vy;
        public float maxDistance;
        public float travelledDistance;
        public boolean active;
        public Color color;
        public ProjectileType type;
        public List<Particle> particles = new ArrayList<>();

        public Projectile(float x, float y, float w, float h, Color color) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.color = color;
            this.active = false;
            this.maxDistance = 250f;
            this.type = ProjectileType.FIRE;
        }

        public void update() {
            if (!active) return;
            x += vx;
            y += vy;
            travelledDistance += (float) Math.hypot(vx, vy);

            // spawn simple particles depending on type
            spawnParticlesForType();

            // update particles
            for (int i = particles.size() - 1; i >= 0; i--) {
                Particle p = particles.get(i);
                p.update();
                if (p.life <= 0) particles.remove(i);
            }

            if (travelledDistance >= maxDistance) {
                active = false;
                particles.clear();
            }
        }

        private void spawnParticlesForType() {
            if (type == ProjectileType.FIRE) {
                // spawn small red/orange particles
                for (int i = 0; i < 2; i++) {
                    float pvx = (RNG.nextFloat() - 0.5f) * 1.5f;
                    float pvy = (RNG.nextFloat() - 0.5f) * 1.5f;
                    Color c = new Color(1f, 0.45f + RNG.nextFloat() * 0.2f, 0.1f, 1f);
                    particles.add(new Particle(x + w / 2f, y + h / 2f, pvx, pvy, 20f + RNG.nextFloat() * 10f, c));
                }
            } else if (type == ProjectileType.ICE) {
                // spawn blue-ish slower particles
                for (int i = 0; i < 1; i++) {
                    float pvx = (RNG.nextFloat() - 0.5f) * 0.6f;
                    float pvy = (RNG.nextFloat() - 0.5f) * 0.6f;
                    Color c = new Color(0.6f, 0.8f, 1f, 1f);
                    particles.add(new Particle(x + w / 2f, y + h / 2f, pvx, pvy, 30f + RNG.nextFloat() * 10f, c));
                }
            } else if (type == ProjectileType.SPARK) {
                // spawn bright quick sparks
                for (int i = 0; i < 3; i++) {
                    float pvx = (RNG.nextFloat() - 0.5f) * 2.5f;
                    float pvy = (RNG.nextFloat() - 0.5f) * 2.5f;
                    Color c = new Color(1f, 0.9f, 0.2f, 1f);
                    particles.add(new Particle(x + w / 2f, y + h / 2f, pvx, pvy, 10f + RNG.nextFloat() * 6f, c));
                }
            }
        }

        public void draw(ShapeRenderer sr) {
            if (active) {
                sr.setColor(color);
                sr.rect(x, y, w, h);
                // draw particles
                for (Particle p : particles) {
                    p.draw(sr);
                }
            }
        }

        public boolean hitsPlayer(float playerX, float playerY, float playerW, float playerH) {
            if (!active) return false;
            return x < playerX + playerW && x + w > playerX && y < playerY + playerH && y + h > playerY;
        }
    }

    public void draw(ShapeRenderer sr) {
        sr.rect(Ex, Ey, Ew, Eh, Ecolor, Ecolor, Ecolor, Ecolor);
        if (projectile != null) {
            projectile.draw(sr);
        }
    }

    public Enemy(int ex, int ey, int ew, int eh, int ecol, int erow, Color ecolor) {
        Ex = ex;
        Ey = ey;
        Ew = ew;
        Eh = eh;
        Ecol = ecol;
        Erow = erow;
        Ecolor = ecolor;
        projectile = new Projectile(ex + ew / 2f, ey + eh / 2f, 10f, 10f, new Color(1f, 0.2f, 0.2f, 1f));
    }

    private ProjectileType chooseTypeByChance() {
        float total = FIRE_CHANCE + ICE_CHANCE + SPARK_CHANCE;
        float r = RNG.nextFloat() * total;
        if (r < FIRE_CHANCE) return ProjectileType.FIRE;
        r -= FIRE_CHANCE;
        if (r < ICE_CHANCE) return ProjectileType.ICE;
        return ProjectileType.SPARK;
    }

    public void fireProjectile(float targetX, float targetY) {
        float startX = Ex + Ew / 2f;
        float startY = Ey + Eh / 2f;
        float dx = targetX - startX;
        float dy = targetY - startY;
        float distance = (float) Math.hypot(dx, dy);

        if (distance == 0) {
            dx = 1f;
            dy = 0f;
            distance = 1f;
        }

        ProjectileType chosen = chooseTypeByChance();

        // configure projectile based on type
        switch (chosen) {
            case FIRE:
                projectile.w = 10f; projectile.h = 10f; projectile.color = new Color(1f, 0.25f, 0.1f, 1f);
                projectile.maxDistance = 250f; // travel distance
                projectile.vx = (dx / distance) * 10f; projectile.vy = (dy / distance) * 10f; break;
            case ICE:
                projectile.w = 12f; projectile.h = 12f; projectile.color = new Color(0.55f, 0.8f, 1f, 1f);
                projectile.maxDistance = 300f; projectile.vx = (dx / distance) * 6f; projectile.vy = (dy / distance) * 6f; break;
            case SPARK:
                projectile.w = 8f; projectile.h = 8f; projectile.color = new Color(1f, 0.95f, 0.2f, 1f);
                projectile.maxDistance = 200f; projectile.vx = (dx / distance) * 12f; projectile.vy = (dy / distance) * 12f; break;
        }

        projectile.type = chosen;
        projectile.x = startX;
        projectile.y = startY;
        projectile.travelledDistance = 0f;
        projectile.active = true;
        projectile.particles.clear();
    }

    public void updateProjectile() {
        if (projectile != null) {
            projectile.update();
        }
    }

    public boolean projectileHitsPlayer(float playerX, float playerY, float playerW, float playerH) {
        return projectile != null && projectile.hitsPlayer(playerX, playerY, playerW, playerH);
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public int getEx() {
        return Ex;
    }

    public void setEx(int ex) {
        Ex = ex;
    }

    public int getEy() {
        return Ey;
    }

    public void setEy(int ey) {
        Ey = ey;
    }

    public int getEw() {
        return Ew;
    }

    public void setEw(int ew) {
        Ew = ew;
    }

    public int getEh() {
        return Eh;
    }

    public void setEh(int eh) {
        Eh = eh;
    }

    public int getEcol() {
        return Ecol;
    }

    public void setEcol(int ecol) {
        Ecol = ecol;
    }

    public int getErow() {
        return Erow;
    }

    public void setErow(int erow) {
        Erow = erow;
    }

    public Color getEcolor() {
        return Ecolor;
    }

    public void setEcolor(Color ecolor) {
        Ecolor = ecolor;
    }
}
