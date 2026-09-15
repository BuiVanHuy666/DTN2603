package com.buivanhuy.services;

public interface Manageable {
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
    void store();

    /**
     * Remove the specified resource from storage.
     */
    void destroy(String id);

    /**
     * Update the specified resource in storage.
     */
    void update();
}