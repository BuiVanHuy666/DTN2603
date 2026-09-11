package lesson_5.backend.interfaces;

import lesson_1.models.Model;

public interface Manageable<T extends Model> {

    /**
     * Display a list of all resources.
     */
    void index();

    /**
     * Display the specified resource by ID.
     */
    void show(String id);

    /**
     * Show the form for creating a new resource.
     */
    void create();

    /**
     * Show the form for editing the specified resource.
     */
    void edit();

    /**
     * Store a newly created resource in storage.
     */
    void store(T model);

    /**
     * Remove the specified resource from storage.
     */
    void destroy(String id);

    /**
     * Update the specified resource in storage.
     */
    void update(T Model);
}